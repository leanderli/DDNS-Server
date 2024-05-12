package cloud.jillion.toolkit.ddns.service.impl;

import cloud.jillion.toolkit.ddns.model.DomainRecord;
import cloud.jillion.toolkit.ddns.service.DomainService;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leanderli
 * @see
 * @since 2021/1/28
 */
@Service
public class AcsDomainServiceImpl implements DomainService {

    public static final Logger LOGGER = LoggerFactory.getLogger(AcsDomainServiceImpl.class);

    @Autowired
    private IAcsClient acsClient;

    @Override
    public List<DomainRecord> getDomainRecords(DomainRecord domain) {
        try {
            DescribeDomainRecordsRequest domainRecordsRequest = new DescribeDomainRecordsRequest();
            domainRecordsRequest.setDomainName(domain.getName());
            DescribeDomainRecordsResponse domainRecordsResponse = acsClient.getAcsResponse(domainRecordsRequest);
            List<DescribeDomainRecordsResponse.Record> domainRecords = domainRecordsResponse.getDomainRecords();
            if (CollectionUtils.isEmpty(domainRecords)) {
                return null;
            }

            List<DomainRecord> domains = new ArrayList<>();
            domainRecords.forEach(domainRecord -> {
                String rr = domainRecord.getRR();
                String domainName = domainRecord.getDomainName();
                if (domain.getSubDomainNames().contains(rr) && domain.getName().equals(domainName)) {
                    domains.add(new DomainRecord(
                            domainRecord.getRecordId(),
                            domainName,
                            domainRecord.getType(),
                            rr,
                            domainRecord.getLine(),
                            domainRecord.getValue(),
                            domainRecord.getTTL(),
                            null
                    ));
                }
            });
            return domains;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateResolveRecord(DomainRecord domainRecord) {
        try {
            UpdateDomainRecordRequest updateRequest = new UpdateDomainRecordRequest();
            // 初始化更新域名解析的类
            updateRequest.setType(domainRecord.getType());
            // 设置新的 IP
            updateRequest.setValue(domainRecord.getRecordValue());
            // 域名
            updateRequest.setRR(domainRecord.getResolveRecord());
            // recordId
            updateRequest.setRecordId(domainRecord.getId());
            acsClient.getAcsResponse(updateRequest);
            return true;
        } catch (ClientException e) {
            e.printStackTrace();
            LOGGER.error("Error!Update resolve record failed,errorMsg="
                    + e.getErrMsg() + ","
                    + domainRecord.toString());
        }
        return false;
    }
}
