package cloud.jillion.toolkit.ddns.service;

import cloud.jillion.toolkit.ddns.model.DomainRecord;

import java.util.List;

/**
 * @author leanderli
 * @see
 * @since 2021/1/28
 */
public interface DomainService {

    List<DomainRecord> getDomainRecords(DomainRecord domainRecord);

    boolean updateResolveRecord(DomainRecord domainRecord);
}
