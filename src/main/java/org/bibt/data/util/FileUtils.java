package org.bibt.data.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 文件工具
 *
 * @author zengfanyong
 * @date 2020/11/19 14:05
 */
@Slf4j
public class FileUtils {

    /** 禁止创建对象 */
    private FileUtils() {
        throw new UnsupportedOperationException("Construct FileUtils");
    }

    /**
     * 获取文件名称的格式后缀
     *
     * @param fileName
     *      文件名称
     * @return
     *      格式后缀
     */
    public static String getSuffix(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }

        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex > 0) {
            return fileName.substring(lastIndex + 1);
        }
        return null;
    }

    /**
     * 创建目录
     *
     * @param dirPath
     *      目录路径
     * @return boolean
     *      是否成功
     *      true：成功
     *      false：失败
     */
    public static boolean mkdir(String dirPath) {
        try {
            org.apache.commons.io.FileUtils.forceMkdir(new File(dirPath));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 写文本到文件
     *
     * @param content
     *      文本
     * @param filePath
     *      文件路径
     * @param append
     *      是否追加写
     * @return
     *      是否成功
     *      true：成功
     *      false：失败
     */
    public static boolean writeContent2File(String content, String filePath, boolean append) {
        boolean writeFlag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File dstFile = new File(filePath);
            if (!dstFile.getParentFile().exists() && !dstFile.getParentFile().mkdirs()) {
                log.error("mkdir parent failed");
                return false;
            }

            bufferedReader = new BufferedReader(new StringReader(content));
            bufferedWriter = new BufferedWriter(new FileWriter(filePath, append));
            char[] buf = new char[1024];
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }

            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            writeFlag = false;
        } finally {
            ConnectionUtils.releaseResource(bufferedReader, bufferedWriter);
        }

        return writeFlag;
    }

    /**
     * 写文本到文件
     * 默认不追加写
     *
     * @param content
     *      文本
     * @param filePath
     *      文件路径
     * @return
     *      是否成功
     *      true：成功
     *      false：失败
     */
    public static boolean writeContent2File(String content, String filePath) {
        return writeContent2File(content, filePath, false);
    }

    /**
     * 删除目录（包含目录下的文件）
     *
     * @param dirPath
     *      目录路径
     * @return boolean
     *      是否成功
     *      true：成功
     *      false：失败
     */
    public static boolean deleteDir(String dirPath) {
        try {
            org.apache.commons.io.FileUtils.deleteDirectory(new File(dirPath));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param filename
     *      文件名称
     * @return boolean
     *      是否成功
     *      true：成功
     *      false：失败
     */
    public static boolean deleteFile(String filename) {
        try {
            org.apache.commons.io.FileUtils.forceDelete(new File(filename));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 获取父目录下所有的目录
     *
     * @param dirPath
     *      父目录
     * @return File[]
     *      所有的目录集合
     */
    public static File[] getAllDir(String dirPath) {
        if (dirPath == null || "".equals(dirPath)) {
            throw new RuntimeException("parentDir can not be empty");
        }

        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            throw new RuntimeException("parentDir not exist, or is not a directory:" + dirPath);
        }

        return file.listFiles(File::isDirectory);
    }

    /**
     * 读取文件的文本内容
     * 可能会发生读取时中断等，抛出运行时异常
     *
     * @param filePath
     *      文件路径
     * @return
     *      文本内容
     */
    public static String readFile2Str(String filePath) {
        File file = new File(filePath);
        FileInputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(file);
            outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            return outputStream.toString();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            ConnectionUtils.releaseResource(inputStream, outputStream);
        }
    }
}
