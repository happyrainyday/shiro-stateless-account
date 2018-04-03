/*
 * <p>ProjectName: aaron</p>
 * <p>PackageName: aaron</p>
 * <p>FileName: MybatisGenerator.java</p>
 * <p>Description: TODO</p>
 * <p>Company: XXXXXX</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * @author xuyanpeng
 * @date 2016年3月19日 下午10:52:42
 * @version 1.0
 */
package mybatis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;


/**
 * Mybatis反向工程类.<br/>
 * Date: 2016年3月19日 下午10:52:42 <br/>
 * @author xuyanpeng
 * @version 1.0
 * @since JDK 1.6
 */
public class MybatisGenerator {
    
    private void generator() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("./src/test/resources/mybatis/mybatis-generator.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
    
    public static void main(String[] args) {
        MybatisGenerator mybatisGenerator = new MybatisGenerator();
        try {
            mybatisGenerator.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
