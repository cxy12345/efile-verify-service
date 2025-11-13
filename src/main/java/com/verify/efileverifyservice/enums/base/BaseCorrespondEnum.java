package com.verify.efileverifyservice.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum BaseCorrespondEnum {
    /**
     * 统计范围类别：
     * 1-水电
     * 2-燃煤
     * 3-燃气
     * 4-风电
     * 5-太阳能
     * 6-核电
     * 7-抽蓄
     * 8-新型储能
     * 9-其他
     *
     *
     * 煤电、气电、抽蓄、风电、光伏、核电、新型储能、其它
     */
    BASE1("水电", "水电", "1","","","",""),
    BASE2("燃煤", "煤电", "2","","","",""),
    BASE3("燃气", "气电", "3","","","",""),
    BASE4("风电", "风电", "4","","","",""),
    BASE5("核电", "核电", "6","","","",""),
    BASE6("新型储能", "新型储能", "8","","","",""),
    BASE7("太阳能", "光伏", "5","","","",""),
    BASE8("抽蓄", "抽蓄", "7","","","",""),
    BASE9("其它", "其它", "9","","","",""),
    BASE10("火电", "火电", "2","","","",""),
    BASE11("储能", "储能", "8","","","",""),
    BASE12("储能", "总值", "9","","","",""),
    BASE13("省内年度", "年度", "1","","","T_BASE_GREEN_ELEC_SETTLE","T_BASE_GREEN_ELEC_SETTLE"),
    BASE14("省内月度", "月度", "2","","","T_BASE_GREEN_ELEC_SETTLE","T_BASE_GREEN_ELEC_SETTLE"),
    BASE15("FREQUENCY_TYPE1", "1", "1","","","T_BASE_ASSIST_SERVICE","T_BASE_ASSIST_SERVICE"),
    BASE16("FREQUENCY_TYPE2", "2", "2","","","T_BASE_ASSIST_SERVICE","T_BASE_ASSIST_SERVICE"),
//    1.水电厂，4.风电厂，2.火电厂，5.光伏电厂
    BASE17("水电厂", "水电厂", "1","","","",""),
    BASE18("风电厂", "风电厂", "4","","","",""),
    BASE19("火电厂", "火电厂", "2","","","",""),
    BASE20("光伏电厂", "光伏电厂", "5","","","",""),

    BASE21("USER_TYPE1", "煤电", "2","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE22("USER_TYPE1", "气电", "2","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE23("USER_TYPE1", "水电", "2","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE24("USER_TYPE1", "核电", "2","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE25("USER_TYPE1", "海上风电", "2","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE26("USER_TYPE1", "陆上风电", "2","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE27("USER_TYPE1", "集中式光伏", "2","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE28("USER_TYPE1", "分布式光伏", "2","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE29("USER_TYPE1", "生物质", "2","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE30("USER_TYPE1", "其它发电企业", "2","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE31("USER_TYPE1", "独立储能", "2","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE32("USER_TYPE1", "虚拟电厂（发电类）", "2","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE33("USER_TYPE1", "抽水蓄能", "2","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE34("USER_TYPE1", "智能微电网", "2","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE35("USER_TYPE1", "其它新型主体", "2","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),

    BASE36("USER_TYPE1", "批发用户", "3","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE37("USER_TYPE1", "零售用户", "3","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE38("USER_TYPE1", "电网代理购电工商业用户", "3","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE39("USER_TYPE1", "独立储能", "3","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE40("USER_TYPE1", "虚拟电厂（负荷类）", "3","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE41("USER_TYPE1", "抽水蓄能", "3","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE42("USER_TYPE1", "智能微电网", "3","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),
    BASE43("USER_TYPE1", "其它新型主体", "3","","","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP",""),

    BASE44("USER_TYPE2", "批发用户","1","批发用户", "1","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE45("USER_TYPE2", "零售用户","2","零售用户", "2","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE46("USER_TYPE2", "电网代理购电","3","电网代理购电", "3","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE47("USER_TYPE2", "售电公司","4","售电公司", "4","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE48("USER_TYPE2", "新型主体","5","新型主体", "5","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE49("USER_TYPE2", "居民用户","6","居民用户", "6","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE50("USER_TYPE2", "农业用户","7","农业用户", "7","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE51("USER_TYPE2", "煤电","10","煤电", "10","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE52("USER_TYPE2", "气电","11","气电", "11","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE53("USER_TYPE2", "水电","3","水电", "3","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE54("USER_TYPE2", "核电","2","核电", "2","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE55("USER_TYPE2", "海上风电","403","海上风电", "403","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE56("USER_TYPE2", "陆上风电","401","陆上风电", "401","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE57("USER_TYPE2", "集中式光伏","411","集中式光伏", "411","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE58("USER_TYPE2", "分布式光伏","413","分布式光伏", "413","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE59("USER_TYPE2", "生物质","42","生物质", "42","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE61("USER_TYPE2", "独立储能","611","独立储能", "611","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE62("USER_TYPE2", "虚拟电厂（发电类）","701","虚拟电厂（发电类）", "701","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE63("USER_TYPE2", "抽水蓄能","601","抽水蓄能", "601","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE64("USER_TYPE2", "智能微电网","71","智能微电网", "71","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE65("USER_TYPE2", "其它新型主体","72","其它新型主体", "72","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),
    BASE74("USER_TYPE2", "累计值","999","累计值", "999","T_BASE_DA_CLR_ELE_PSUM_CMP,T_BASE_RT_CLR_ELE_PSUM_CMP,T_BASE_SETTLE_GEN_MON,T_BASE_SETTLE_GEN_YEAR,T_BASE_GENERATION_COMPOSITION",""),

    BASE66("新型主体构成-独立储能", "独立储能","1","", "","T_BASE_NEW_ENTITY_COMPOSITION",""),
    BASE68("新型主体构成-虚拟电厂（发电类）", "虚拟电厂（发电类）","21","", "","T_BASE_NEW_ENTITY_COMPOSITION",""),
    BASE69("新型主体构成-虚拟电厂（负荷类）", "虚拟电厂（负荷类）","22","", "","T_BASE_NEW_ENTITY_COMPOSITION",""),
    BASE70("新型主体构成-抽水蓄能", "抽水蓄能","3","", "","T_BASE_NEW_ENTITY_COMPOSITION",""),
    BASE71("新型主体构成-智能微电网", "智能微电网","4","", "","T_BASE_NEW_ENTITY_COMPOSITION",""),
    BASE72("新型主体构成-其它新型主体", "其它新型主体","5","", "","T_BASE_NEW_ENTITY_COMPOSITION",""),
    BASE73("新型主体构成-累计值", "累计值","999","", "","T_BASE_NEW_ENTITY_COMPOSITION",""),

    BASE75("用户侧-批发用户", "批发用户","1","", "","T_BASE_SETTLE_USER_MON,T_BASE_SETTLE_USER_YEAR",""),
    BASE76("用户侧-零售用户（售电公司）", "零售用户（售电公司）","2","", "","T_BASE_SETTLE_USER_MON,T_BASE_SETTLE_USER_YEAR",""),
    BASE77("用户侧-电网代理购电工商业用户（市场化部分）", "电网代理购电工商业用户（市场化部分）","3","", "","T_BASE_SETTLE_USER_MON,T_BASE_SETTLE_USER_YEAR",""),
    BASE78("用户侧-独立储能", "独立储能","51","", "","T_BASE_SETTLE_USER_MON,T_BASE_SETTLE_USER_YEAR",""),
    BASE79("用户侧-虚拟电厂（负荷类）", "虚拟电厂（负荷类）","52","", "","T_BASE_SETTLE_USER_MON,T_BASE_SETTLE_USER_YEAR",""),
    BASE80("用户侧-抽水蓄能", "抽水蓄能","54","", "","T_BASE_SETTLE_USER_MON,T_BASE_SETTLE_USER_YEAR",""),
    BASE81("用户侧-其它新型主体", "其它新型主体","53","", "","T_BASE_SETTLE_USER_MON,T_BASE_SETTLE_USER_YEAR",""),
    BASE82("用户侧-累计值", "累计值","999","", "","T_BASE_SETTLE_USER_MON,T_BASE_SETTLE_USER_YEAR",""),

    ;

    private final String name;

    private final String dbsType;

    private final String type;

    private final String dbsType2;

    private final String type2;

    private final String modelName;

    private final String tableName;



    public static BaseCorrespondEnum ofName(String name) {
        return Arrays.stream(values()).filter(BASEEnum -> BASEEnum.name.equalsIgnoreCase(name)).findFirst()
                .orElse(null);
    }
    
    public static BaseCorrespondEnum ofDbsType(String dbsType) {
        return Arrays.stream(values()).filter(BASEEnum -> BASEEnum.dbsType.equalsIgnoreCase(dbsType)).findFirst()
                .orElse(null);
    }

    public static List<BaseCorrespondEnum> ofDbsTableName(String tableName) {
        return Arrays.stream(values()).filter(BASEEnum -> BASEEnum.tableName.contains(tableName)).collect(Collectors.toList());
    }


    public static BaseCorrespondEnum ofModelNameAndType1(String modelName, String type1) {
        return Arrays.stream(values()).filter(BASEEnum -> BASEEnum.modelName.contains(modelName) && BASEEnum.getDbsType().equalsIgnoreCase(type1)).findFirst()
                .orElse(null);
    }

    public static BaseCorrespondEnum ofModelNameAndType2(String modelName, String type2) {
        return Arrays.stream(values()).filter(BASEEnum -> BASEEnum.modelName.contains(modelName) && BASEEnum.getDbsType2().equalsIgnoreCase(type2)).findFirst()
                .orElse(null);
    }

}
