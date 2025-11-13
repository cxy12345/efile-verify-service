package com.verify.efileverifyservice.util;

import cn.hutool.core.util.ZipUtil;
import com.alibaba.druid.util.StringUtils;
import com.verify.efileverifyservice.entity.FileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class EfileUtil {

    @Value("${efile.url}")
    private String efileUrl;


    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd_HH:mm:ss");

    /**
     * 写入e文件内容
     *
     * @param list
     * @throws IOException
     */
    public void fileWrite(List<?> list, String fileName, String typeName, Class<?> clazz, String supplementDate) throws Exception {
//        String date = LocalDateTime.now().format(dateFormat);
        String date = StringUtils.isEmpty(supplementDate) ? LocalDateTime.now().format(dateFormat) : supplementDate.replace("-", "") + "_00:00:00";
        ///写入数据
        String filePath = efileUrl + fileName + ".txt";
        // 相对路径，如果没有则要建立一个新的output.txt文件
        File writeName = new File(filePath);
        File parentFile = writeName.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdir();
        }
        // 创建新文件,有同名的文件的话直接覆盖
        writeName.createNewFile();
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeName), "GBK")));
        // \r\n即为换行
        //文件头
        out.write("<! Entity=PCSYN dataTime='" + date + "'!>");
        out.write("\r\n");
        out.write("<DATA::"+ typeName +" num="+ list.size() +">");
        out.write("\r\n");
        // 字段名称
        String fieldMessage = EntityUtil.getFieldInfo(clazz);
        out.write(fieldMessage);
        out.write("\r\n");
        String fieldNote = EntityUtil.getApiModelPropertyInfo(clazz);
        out.write(fieldNote);
        out.write("\r\n");
        //数据记录
        String data = EntityUtil.convertEntityListToString(list);
        out.write(data);
        // 把缓存区内容写入文件
        out.flush();

    }


    public MultipartFile fileConverts(File file,FileInfo fileInfo) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        MultipartFile multipartFile = new ByteToMultipartFile(
                fileContent,
                fileInfo.getOriginalFilename(),  // name参数
                fileInfo.getOriginalFilename(),  // originalFilename参数
                Files.probeContentType(file.toPath())  // contentType参数
        );
        return multipartFile;
    }

    /**
     * 把文件夹打包成压缩包,并给压缩包命名
     *
     * @param zipFileName 压缩包文件名（不含.zip扩展名）
     * @return 是否压缩成功
     */
    public boolean zipFolder(String zipFileName) {
        File sourceFolder = new File(efileUrl);
        // 将压缩文件放在源文件夹的上级目录中，避免嵌套问题
        File parentDir = sourceFolder.getParentFile();
        File zipFile = new File(parentDir, zipFileName + ".zip");
        return ZipUtil.zip(sourceFolder.getAbsolutePath(), zipFile.getAbsolutePath()) != null;
    }

    /**
     * 把文件夹打包成压缩包
     */
    public boolean zipFolder() {
        File file = new File(efileUrl);
        return ZipUtil.zip(file) != null;
    }





}
