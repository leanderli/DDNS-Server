package cn.byteflashing.ddns.core.service;

import cn.byteflashing.ddns.core.domain.DomainRecord;

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
