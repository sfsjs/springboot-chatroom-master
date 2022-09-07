package com.xuwei.chatroom;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * mybatis-plus自动生成代码
 */
public class GenerateCode {
    /**
     * 项目位置:PROJECT_PATH
     * 作者名:AUTHOR_NAME
     * 父包名:PACKAGE_PARENT
     * 映射的数据库表的表名:tables
     */
    private final static String PROJECT_PATH = System.getProperty("user.dir");
    private final static String AUTHOR_NAME = "xuwei";
    private final static String PACKAGE_PARENT = "com.xuwei.chatroom";
    private static final String[] tables = {"user"};
    /**
     * 代码生成器:autoGenerator
     * 全局配置:globalConfig
     */
    private final static AutoGenerator autoGenerator = new AutoGenerator();
    private final static GlobalConfig globalConfig = new GlobalConfig();
    /**
     * 数据库URL:URL
     * 数据库驱动:DRIVER
     * 数据库用户名:USERNAME
     * 数据库密码:PASSWORD
     */
    private final static String URL = "jdbc:mysql://localhost:3306/chatroom?characterEncoding=UTF-8&serverTimezone=UTC&useUnicode=true";
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "123456";


    public static void main(String[] args) {
        //全局设置
        setPropertyConfig();
        //数据源配置
        setDataSourceConfig();
        //策略配置
        setStrategyConfig();
        //包配置
        setPackageConfig();

        //执行
        autoGenerator.execute();
    }

    /**
     * 包配置
     */
    private static void setPackageConfig() {
        PackageConfig packageConfig = new PackageConfig();
        // packageConfig.setModuleName("模块名");  //生成的模块
        packageConfig.setParent(PACKAGE_PARENT); //自定义包路径
        packageConfig.setEntity("entity");//实体类包名
        packageConfig.setService("service");//service包名
        packageConfig.setService("service.impl");//serviceImpl包名
        packageConfig.setController("controller");//controller包名，默认是web
        packageConfig.setMapper("mapper");//mapper包名
        packageConfig.setXml("mapper");//mapper的xml包名

        autoGenerator.setPackageInfo(packageConfig);


        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";
        //文件输出位置自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig() {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名,如果你 Entity 设置了前后缀。此处注意 xml 的名称会跟着发生变化！！
                return PROJECT_PATH + "/src/main/resources/mapper/" + packageConfig.getModuleName()
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);

        autoGenerator.setCfg(cfg);
    }

    /**
     * 策略配置
     */
    private static void setStrategyConfig() {
        StrategyConfig strategy = new StrategyConfig();

        strategy.setInclude(tables);//设置映射的表名
        //数据库表->实体，下划线转驼峰命名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setTablePrefix("t_");//表名前缀

        strategy.setEntityLombokModel(true);//是否使用lombok
        strategy.setRestControllerStyle(true);//设置restful风格
//        strategy.setLogicDeleteFieldName("deleted");//逻辑删除
//        strategy.setVersionFieldName("version");//乐观锁

        //时间列自动填充配置
//        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
//        TableFill updateTime = new TableFill("update_time", FieldFill.INSERT_UPDATE);
//        strategy.setTableFillList(Arrays.asList(createTime, updateTime));

        autoGenerator.setStrategy(strategy);
    }

    /**
     * 全局设置
     */
    private static void setPropertyConfig() {
        //代码输出目录
        globalConfig.setOutputDir(PROJECT_PATH + "/src/main/java");
        globalConfig.setAuthor(AUTHOR_NAME);//作者名字
        globalConfig.setOpen(false);//是否打开当前项目文件夹
        globalConfig.setFileOverride(true);//是否覆盖原来的代码

        // globalConfig.setServiceName("%sService");//去掉IService的I
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setServiceName("I%sService");
        globalConfig.setServiceImplName("I%sServiceImpl");
        globalConfig.setControllerName("%sController");

        globalConfig.setIdType(IdType.AUTO);//数据库ID生成方式
        globalConfig.setDateType(DateType.ONLY_DATE);//时间类型对应策略

        globalConfig.setEnableCache(false);// XML 二级缓存
        globalConfig.setBaseResultMap(true);// XML ResultMap
        globalConfig.setBaseColumnList(true);// XML columnList
        globalConfig.setSwagger2(true);//开启swagger2

        autoGenerator.setGlobalConfig(globalConfig);
    }

    /**
     * 数据源配置
     */
    private static void setDataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(URL);
        dataSourceConfig.setDriverName(DRIVER);
        dataSourceConfig.setUsername(USERNAME);
        dataSourceConfig.setPassword(PASSWORD);
        dataSourceConfig.setDbType(DbType.MYSQL);//数据库类型

        autoGenerator.setDataSource(dataSourceConfig);
    }
}
