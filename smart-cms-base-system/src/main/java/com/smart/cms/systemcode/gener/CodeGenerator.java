package com.smart.cms.systemcode.gener;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.smart.cms.utils.other.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/21 18:06
 * @Version: 1.0
 */
public class CodeGenerator {
    private static final Logger log = LoggerFactory.getLogger(CodeGenerator.class);
    private String systemName = "sword";
    private String codeName;
    private String serviceName = "smart-service";
    private String packageName = "com.smart.cms";
    private String packageDir;
    private String packageWebDir;
    private String[] tablePrefix = new String[]{"smart_"};
    private String[] includeTables = new String[]{"smart_test"};
    private String[] excludeTables = new String[0];
    private Boolean hasSuperEntity;
    private Boolean hasWrapper;
    private String[] superEntityColumns;
    private String tenantColumn;
    private Boolean isSwagger2;
    private String driverName;
    private String url;
    private String username;
    private String password;

    public void run() {
        Properties props = this.getProperties();
        AutoGenerator mpg = new AutoGenerator();
        GlobalConfig gc = new GlobalConfig();
        String outputDir = this.getOutputDir();
        String author = props.getProperty("author");
        gc.setOutputDir(outputDir);
        gc.setAuthor(author);
        gc.setFileOverride(true);
        gc.setOpen(false);
        gc.setActiveRecord(false);
        gc.setEnableCache(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setSwagger2(this.isSwagger2);
        mpg.setGlobalConfig(gc);
        DataSourceConfig dsc = new DataSourceConfig();
        String driverName = this.driverName;
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert());

        dsc.setDriverName(driverName);
        dsc.setUrl(this.url);
        dsc.setUsername(this.username);
        dsc.setPassword(this.password);
        mpg.setDataSource(dsc);
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix(this.tablePrefix);
        if (this.includeTables.length > 0) {
            strategy.setInclude(this.includeTables);
        }

        if (this.excludeTables.length > 0) {
            strategy.setExclude(this.excludeTables);
        }

        if (this.hasSuperEntity) {
            strategy.setSuperEntityClass("com.smart.cms.mp.base.BaseEntity");
            strategy.setSuperEntityColumns(this.superEntityColumns);
            strategy.setSuperServiceClass("com.smart.cms.mp.base.BaseService");
            strategy.setSuperServiceImplClass("com.smart.cms.mp.base.BaseServiceImpl");
        } else {
            strategy.setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService");
            strategy.setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
        }

        strategy.setSuperControllerClass("com.smart.cms.boot.ctrl.BladeController");
        strategy.setEntityBuilderModel(false);
        strategy.setEntityLombokModel(true);
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        PackageConfig pc = new PackageConfig();
        pc.setModuleName((String)null);
        pc.setParent(this.packageName);
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);
        mpg.setCfg(this.getInjectionConfig());
        mpg.execute();
    }

    private InjectionConfig getInjectionConfig() {
        final String servicePackage = this.serviceName.split("-").length > 1 ? this.serviceName.split("-")[1] : this.serviceName;
        final Map<String, Object> map = new HashMap(16);
        InjectionConfig cfg = new InjectionConfig() {
            public void initMap() {
                map.put("codeName", CodeGenerator.this.codeName);
                map.put("serviceName", CodeGenerator.this.serviceName);
                map.put("servicePackage", servicePackage);
                map.put("servicePackageLowerCase", servicePackage.toLowerCase());
                map.put("tenantColumn", CodeGenerator.this.tenantColumn);
                map.put("hasWrapper", CodeGenerator.this.hasWrapper);
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList();
        focList.add(new FileOutConfig("/templates/sql/menu.sql.vm") {
            public String outputFile(TableInfo tableInfo) {
                map.put("entityKey", tableInfo.getEntityName().toLowerCase());
                map.put("menuId", IdWorker.getId());
                map.put("addMenuId", IdWorker.getId());
                map.put("editMenuId", IdWorker.getId());
                map.put("removeMenuId", IdWorker.getId());
                map.put("viewMenuId", IdWorker.getId());
                return CodeGenerator.this.getOutputDir() + "//sql/" + tableInfo.getEntityName().toLowerCase() + ".menu.mysql";
            }
        });
        focList.add(new FileOutConfig("/templates/entityVO.java.vm") {
            public String outputFile(TableInfo tableInfo) {
                return CodeGenerator.this.getOutputDir() + "/" + CodeGenerator.this.packageName.replace(".", "/") + "/vo/" + tableInfo.getEntityName() + "VO" + ".java";
            }
        });
        focList.add(new FileOutConfig("/templates/entityDTO.java.vm") {
            public String outputFile(TableInfo tableInfo) {
                return CodeGenerator.this.getOutputDir() + "/" + CodeGenerator.this.packageName.replace(".", "/") + "/dto/" + tableInfo.getEntityName() + "DTO" + ".java";
            }
        });
        if (this.hasWrapper) {
            focList.add(new FileOutConfig("/templates/wrapper.java.vm") {
                public String outputFile(TableInfo tableInfo) {
                    return CodeGenerator.this.getOutputDir() + "/" + CodeGenerator.this.packageName.replace(".", "/") + "/wrapper/" + tableInfo.getEntityName() + "Wrapper" + ".java";
                }
            });
        }

        if (StringUtils.isNotBlank(this.packageWebDir)) {
            if (Objects.equals(this.systemName, "sword")) {
                focList.add(new FileOutConfig("/templates/sword/action.js.vm") {
                    public String outputFile(TableInfo tableInfo) {
                        return CodeGenerator.this.getOutputWebDir() + "/actions/" + tableInfo.getEntityName().toLowerCase() + ".js";
                    }
                });
                focList.add(new FileOutConfig("/templates/sword/model.js.vm") {
                    public String outputFile(TableInfo tableInfo) {
                        return CodeGenerator.this.getOutputWebDir() + "/models/" + tableInfo.getEntityName().toLowerCase() + ".js";
                    }
                });
                focList.add(new FileOutConfig("/templates/sword/service.js.vm") {
                    public String outputFile(TableInfo tableInfo) {
                        return CodeGenerator.this.getOutputWebDir() + "/services/" + tableInfo.getEntityName().toLowerCase() + ".js";
                    }
                });
                focList.add(new FileOutConfig("/templates/sword/list.js.vm") {
                    public String outputFile(TableInfo tableInfo) {
                        return CodeGenerator.this.getOutputWebDir() + "/pages/" + StringUtil.firstCharToUpper(servicePackage) + "/" + tableInfo.getEntityName() + "/" + tableInfo.getEntityName() + ".js";
                    }
                });
                focList.add(new FileOutConfig("/templates/sword/add.js.vm") {
                    public String outputFile(TableInfo tableInfo) {
                        return CodeGenerator.this.getOutputWebDir() + "/pages/" + StringUtil.firstCharToUpper(servicePackage) + "/" + tableInfo.getEntityName() + "/" + tableInfo.getEntityName() + "Add.js";
                    }
                });
                focList.add(new FileOutConfig("/templates/sword/edit.js.vm") {
                    public String outputFile(TableInfo tableInfo) {
                        return CodeGenerator.this.getOutputWebDir() + "/pages/" + StringUtil.firstCharToUpper(servicePackage) + "/" + tableInfo.getEntityName() + "/" + tableInfo.getEntityName() + "Edit.js";
                    }
                });
                focList.add(new FileOutConfig("/templates/sword/view.js.vm") {
                    public String outputFile(TableInfo tableInfo) {
                        return CodeGenerator.this.getOutputWebDir() + "/pages/" + StringUtil.firstCharToUpper(servicePackage) + "/" + tableInfo.getEntityName() + "/" + tableInfo.getEntityName() + "View.js";
                    }
                });
            } else if (Objects.equals(this.systemName, "saber")) {
                focList.add(new FileOutConfig("/templates/saber/api.js.vm") {
                    public String outputFile(TableInfo tableInfo) {
                        return CodeGenerator.this.getOutputWebDir() + "/api/" + servicePackage.toLowerCase() + "/" + tableInfo.getEntityName().toLowerCase() + ".js";
                    }
                });
                focList.add(new FileOutConfig("/templates/saber/crud.vue.vm") {
                    public String outputFile(TableInfo tableInfo) {
                        return CodeGenerator.this.getOutputWebDir() + "/views/" + servicePackage.toLowerCase() + "/" + tableInfo.getEntityName().toLowerCase() + ".vue";
                    }
                });
            }
        }

        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    private Properties getProperties() {
        Resource resource = new ClassPathResource("/templates/code.properties");
        Properties props = new Properties();

        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return props;
    }

    public String getOutputDir() {
        return (StringUtils.isBlank(this.packageDir) ? System.getProperty("user.dir") + "/smart-cms-base-common" : this.packageDir) + "/src/main/java";
    }

    public String getOutputWebDir() {
        return (StringUtils.isBlank(this.packageWebDir) ? System.getProperty("user.dir") : this.packageWebDir) + "/src";
    }

    private String getGeneratorViewPath(String viewOutputDir, TableInfo tableInfo, String suffixPath) {
        String name = StringUtils.firstToLowerCase(tableInfo.getEntityName());
        String path = viewOutputDir + "/" + name + "/" + name + suffixPath;
        File viewDir = (new File(path)).getParentFile();
        if (!viewDir.exists()) {
            viewDir.mkdirs();
        }

        return path;
    }

    public CodeGenerator() {
        this.hasSuperEntity = Boolean.FALSE;
        this.hasWrapper = Boolean.FALSE;
        this.superEntityColumns = new String[]{"id", "create_time", "create_code", "update_time", "update_code", "del_flag"};
        this.tenantColumn = "tenant_id";
        this.isSwagger2 = Boolean.TRUE;
    }

    public String getSystemName() {
        return this.systemName;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getPackageDir() {
        return this.packageDir;
    }

    public String getPackageWebDir() {
        return this.packageWebDir;
    }

    public String[] getTablePrefix() {
        return this.tablePrefix;
    }

    public String[] getIncludeTables() {
        return this.includeTables;
    }

    public String[] getExcludeTables() {
        return this.excludeTables;
    }

    public Boolean getHasSuperEntity() {
        return this.hasSuperEntity;
    }

    public Boolean getHasWrapper() {
        return this.hasWrapper;
    }

    public String[] getSuperEntityColumns() {
        return this.superEntityColumns;
    }

    public String getTenantColumn() {
        return this.tenantColumn;
    }

    public Boolean getIsSwagger2() {
        return this.isSwagger2;
    }

    public String getDriverName() {
        return this.driverName;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setSystemName(final String systemName) {
        this.systemName = systemName;
    }

    public void setCodeName(final String codeName) {
        this.codeName = codeName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public void setPackageName(final String packageName) {
        this.packageName = packageName;
    }

    public void setPackageDir(final String packageDir) {
        this.packageDir = packageDir;
    }

    public void setPackageWebDir(final String packageWebDir) {
        this.packageWebDir = packageWebDir;
    }

    public void setTablePrefix(final String[] tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public void setIncludeTables(final String[] includeTables) {
        this.includeTables = includeTables;
    }

    public void setExcludeTables(final String[] excludeTables) {
        this.excludeTables = excludeTables;
    }

    public void setHasSuperEntity(final Boolean hasSuperEntity) {
        this.hasSuperEntity = hasSuperEntity;
    }

    public void setHasWrapper(final Boolean hasWrapper) {
        this.hasWrapper = hasWrapper;
    }

    public void setSuperEntityColumns(final String[] superEntityColumns) {
        this.superEntityColumns = superEntityColumns;
    }

    public void setTenantColumn(final String tenantColumn) {
        this.tenantColumn = tenantColumn;
    }

    public void setIsSwagger2(final Boolean isSwagger2) {
        this.isSwagger2 = isSwagger2;
    }

    public void setDriverName(final String driverName) {
        this.driverName = driverName;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CodeGenerator)) {
            return false;
        } else {
            CodeGenerator other = (CodeGenerator)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$hasSuperEntity = this.getHasSuperEntity();
                Object other$hasSuperEntity = other.getHasSuperEntity();
                if (this$hasSuperEntity == null) {
                    if (other$hasSuperEntity != null) {
                        return false;
                    }
                } else if (!this$hasSuperEntity.equals(other$hasSuperEntity)) {
                    return false;
                }

                Object this$hasWrapper = this.getHasWrapper();
                Object other$hasWrapper = other.getHasWrapper();
                if (this$hasWrapper == null) {
                    if (other$hasWrapper != null) {
                        return false;
                    }
                } else if (!this$hasWrapper.equals(other$hasWrapper)) {
                    return false;
                }

                Object this$isSwagger2 = this.getIsSwagger2();
                Object other$isSwagger2 = other.getIsSwagger2();
                if (this$isSwagger2 == null) {
                    if (other$isSwagger2 != null) {
                        return false;
                    }
                } else if (!this$isSwagger2.equals(other$isSwagger2)) {
                    return false;
                }

                label174: {
                    Object this$systemName = this.getSystemName();
                    Object other$systemName = other.getSystemName();
                    if (this$systemName == null) {
                        if (other$systemName == null) {
                            break label174;
                        }
                    } else if (this$systemName.equals(other$systemName)) {
                        break label174;
                    }

                    return false;
                }

                label167: {
                    Object this$codeName = this.getCodeName();
                    Object other$codeName = other.getCodeName();
                    if (this$codeName == null) {
                        if (other$codeName == null) {
                            break label167;
                        }
                    } else if (this$codeName.equals(other$codeName)) {
                        break label167;
                    }

                    return false;
                }

                Object this$serviceName = this.getServiceName();
                Object other$serviceName = other.getServiceName();
                if (this$serviceName == null) {
                    if (other$serviceName != null) {
                        return false;
                    }
                } else if (!this$serviceName.equals(other$serviceName)) {
                    return false;
                }

                label153: {
                    Object this$packageName = this.getPackageName();
                    Object other$packageName = other.getPackageName();
                    if (this$packageName == null) {
                        if (other$packageName == null) {
                            break label153;
                        }
                    } else if (this$packageName.equals(other$packageName)) {
                        break label153;
                    }

                    return false;
                }

                label146: {
                    Object this$packageDir = this.getPackageDir();
                    Object other$packageDir = other.getPackageDir();
                    if (this$packageDir == null) {
                        if (other$packageDir == null) {
                            break label146;
                        }
                    } else if (this$packageDir.equals(other$packageDir)) {
                        break label146;
                    }

                    return false;
                }

                Object this$packageWebDir = this.getPackageWebDir();
                Object other$packageWebDir = other.getPackageWebDir();
                if (this$packageWebDir == null) {
                    if (other$packageWebDir != null) {
                        return false;
                    }
                } else if (!this$packageWebDir.equals(other$packageWebDir)) {
                    return false;
                }

                if (!Arrays.deepEquals(this.getTablePrefix(), other.getTablePrefix())) {
                    return false;
                } else if (!Arrays.deepEquals(this.getIncludeTables(), other.getIncludeTables())) {
                    return false;
                } else if (!Arrays.deepEquals(this.getExcludeTables(), other.getExcludeTables())) {
                    return false;
                } else if (!Arrays.deepEquals(this.getSuperEntityColumns(), other.getSuperEntityColumns())) {
                    return false;
                } else {
                    Object this$tenantColumn = this.getTenantColumn();
                    Object other$tenantColumn = other.getTenantColumn();
                    if (this$tenantColumn == null) {
                        if (other$tenantColumn != null) {
                            return false;
                        }
                    } else if (!this$tenantColumn.equals(other$tenantColumn)) {
                        return false;
                    }

                    label119: {
                        Object this$driverName = this.getDriverName();
                        Object other$driverName = other.getDriverName();
                        if (this$driverName == null) {
                            if (other$driverName == null) {
                                break label119;
                            }
                        } else if (this$driverName.equals(other$driverName)) {
                            break label119;
                        }

                        return false;
                    }

                    Object this$url = this.getUrl();
                    Object other$url = other.getUrl();
                    if (this$url == null) {
                        if (other$url != null) {
                            return false;
                        }
                    } else if (!this$url.equals(other$url)) {
                        return false;
                    }

                    label105: {
                        Object this$username = this.getUsername();
                        Object other$username = other.getUsername();
                        if (this$username == null) {
                            if (other$username == null) {
                                break label105;
                            }
                        } else if (this$username.equals(other$username)) {
                            break label105;
                        }

                        return false;
                    }

                    Object this$password = this.getPassword();
                    Object other$password = other.getPassword();
                    if (this$password == null) {
                        if (other$password != null) {
                            return false;
                        }
                    } else if (!this$password.equals(other$password)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CodeGenerator;
    }

    public int hashCode() {
        int result = 1;
        Object $hasSuperEntity = this.getHasSuperEntity();
        result = result * 59 + ($hasSuperEntity == null ? 43 : $hasSuperEntity.hashCode());
        Object $hasWrapper = this.getHasWrapper();
        result = result * 59 + ($hasWrapper == null ? 43 : $hasWrapper.hashCode());
        Object $isSwagger2 = this.getIsSwagger2();
        result = result * 59 + ($isSwagger2 == null ? 43 : $isSwagger2.hashCode());
        Object $systemName = this.getSystemName();
        result = result * 59 + ($systemName == null ? 43 : $systemName.hashCode());
        Object $codeName = this.getCodeName();
        result = result * 59 + ($codeName == null ? 43 : $codeName.hashCode());
        Object $serviceName = this.getServiceName();
        result = result * 59 + ($serviceName == null ? 43 : $serviceName.hashCode());
        Object $packageName = this.getPackageName();
        result = result * 59 + ($packageName == null ? 43 : $packageName.hashCode());
        Object $packageDir = this.getPackageDir();
        result = result * 59 + ($packageDir == null ? 43 : $packageDir.hashCode());
        Object $packageWebDir = this.getPackageWebDir();
        result = result * 59 + ($packageWebDir == null ? 43 : $packageWebDir.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getTablePrefix());
        result = result * 59 + Arrays.deepHashCode(this.getIncludeTables());
        result = result * 59 + Arrays.deepHashCode(this.getExcludeTables());
        result = result * 59 + Arrays.deepHashCode(this.getSuperEntityColumns());
        Object $tenantColumn = this.getTenantColumn();
        result = result * 59 + ($tenantColumn == null ? 43 : $tenantColumn.hashCode());
        Object $driverName = this.getDriverName();
        result = result * 59 + ($driverName == null ? 43 : $driverName.hashCode());
        Object $url = this.getUrl();
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        Object $username = this.getUsername();
        result = result * 59 + ($username == null ? 43 : $username.hashCode());
        Object $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        return result;
    }
}
