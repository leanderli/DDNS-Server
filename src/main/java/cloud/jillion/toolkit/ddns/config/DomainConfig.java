package cloud.jillion.toolkit.ddns.config;

import cloud.jillion.toolkit.ddns.common.util.ResourceUtils;
import cloud.jillion.toolkit.ddns.model.DomainRecord;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author leanderli
 * @see
 * @since 2021/2/1
 */
public class DomainConfig {
    private static final String CONFIG_FILE = "domain-config.json";

    private static DomainRecord domainRecord;

    static {
        String configJson = ResourceUtils.getStringContentFromFile(CONFIG_FILE);
        if (StringUtils.isBlank(configJson)) {
            throw new IllegalArgumentException("domain-config file is empty");
        }
        JsonObject configObj = new JsonParser().parse(configJson).getAsJsonObject();
        String rootDomainName = configObj.get("rootDomain").getAsString();
        if (StringUtils.isBlank(rootDomainName)) {
            throw new IllegalArgumentException("domain-config file is invalid.rootDomain is empty");
        }
        List<String> subDomainNames = new Gson().fromJson(configObj.get("subDomainNames"), new TypeToken<List<String>>() {
        }.getType());
        if (CollectionUtils.isEmpty(subDomainNames)) {
            throw new IllegalArgumentException("domain-config file is invalid.subDomainNames is empty");
        }
        domainRecord = new DomainRecord();
        domainRecord.setName(rootDomainName);
        domainRecord.setSubDomainNames(subDomainNames);
    }

    public static DomainRecord getConfiguredDomain() {
        return domainRecord;
    }
}
