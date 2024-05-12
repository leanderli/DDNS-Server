package cloud.jillion.toolkit.ddns.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author leanderli
 * @see
 * @since 2019/7/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DomainRecord {

    /**
     * ID
     */
    private String id;
    /**
     * 域名名
     */
    private String name;
    /**
     * 记录类型
     */
    private String type;
    /**
     * 主机记录
     */
    private String resolveRecord;
    /**
     * 解析线路
     */
    private String resolveLine;
    /**
     * 记录值
     */
    private String recordValue;
    /**
     * TTL
     */
    private Long ttl;

    private List<String> subDomainNames;


}
