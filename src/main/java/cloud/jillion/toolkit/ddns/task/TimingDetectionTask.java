package cloud.jillion.toolkit.ddns.task;

import cloud.jillion.toolkit.ddns.common.util.NetworkUtils;
import cloud.jillion.toolkit.ddns.config.DomainConfig;
import cloud.jillion.toolkit.ddns.model.DomainRecord;
import cloud.jillion.toolkit.ddns.service.DomainService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author leanderli
 * @see
 * @since 2021/1/29
 */
@Component
public class TimingDetectionTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimingDetectionTask.class);

    private static String mCurrentIP;

    @Autowired
    private DomainService domainService;

    @Scheduled(cron = "0/30 * * * * ?")
    public void ipDetectionExecute() {
        LOGGER.info("开始执行IP检测任务");
        if (isIpChanged()) {
            List<DomainRecord> records = domainService.getDomainRecords(DomainConfig.getConfiguredDomain());
            if (CollectionUtils.isEmpty(records)) {
                LOGGER.info("没有需要更新的域名记录");
                return;
            }
            records.forEach(record -> {
                if (record.getRecordValue().equals(mCurrentIP)) {
                    return;
                }
                record.setRecordValue(mCurrentIP);
                boolean result = domainService.updateResolveRecord(record);
                if (result) {
                    LOGGER.info(record.toString() + " 更新成功");
                }
            });

        }
        LOGGER.info("IP检测任务执行完成");
    }

    private boolean isIpChanged() {
        if (StringUtils.isBlank(mCurrentIP)) {
            String newIp = NetworkUtils.getV4Ip();
            if (StringUtils.isNotBlank(newIp)) {
                mCurrentIP = newIp;
                return true;
            }
        } else {
            String newIp = NetworkUtils.getV4Ip();
            if (StringUtils.isNotBlank(newIp) && !mCurrentIP.equals(newIp)) {
                mCurrentIP = newIp;
                return true;
            }
        }
        return false;
    }
}
