package org.bibt.data.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

import static com.fasterxml.jackson.databind.DeserializationFeature.*;
import static com.fasterxml.jackson.databind.MapperFeature.REQUIRE_SETTERS_FOR_GETTERS;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * json工具
 * 
 * @author zengfanyong
 * @date 2020/11/18 10:23
 */
@Slf4j
public class JsonUtils {

    /** 静态单例对象，引入后能确保重复使用 */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
            .configure(READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
            .configure(REQUIRE_SETTERS_FOR_GETTERS, true)
            .setTimeZone(TimeZone.getDefault());


    /** 禁止创建对象 */
    private JsonUtils() {
        throw new UnsupportedOperationException("Construct JsonUtils");
    }

    /**
     * 创建数组节点
     *
     * @return ArrayNode
     *      数组节点
     */
    public static ArrayNode createArrayNode() {
        return OBJECT_MAPPER.createArrayNode();
    }

    /**
     * 创建Object节点
     *
     * @return ObjectNode
     *      Object节点
     */
    public static ObjectNode createObjectNode() {
        return OBJECT_MAPPER.createObjectNode();
    }

    /**
     * 创建Json节点
     *
     * @return JsonNode
     *      Json节点
     */
    public static JsonNode toJsonNode(final Object obj) {
        return OBJECT_MAPPER.valueToTree(obj);
    }

    /**
     * Object对象 转 Json字符串
     *
     * @param object
     *      Object对象
     * @param feature
     *      序列化特征值
     * @return String
     *      Json字符串
     */
    public static String toJsonString(final Object object, final SerializationFeature feature) {
        try {
            final ObjectWriter writer = OBJECT_MAPPER.writer(feature);
            return writer.writeValueAsString(object);
        } catch (Exception e) {
            log.error("object to json exception!", e);
        }

        return null;
    }

    /**
     * Json字符串 转 指定类的对象
     *
     * @param json
     *      Json字符串
     * @param clazz
     *      指定类的类信息
     * @param <T>
     *      Object类型
     * @return T
     *      指定类的对象
     */
    public static <T> T parseObject(final String json, final Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            log.error("parse object exception!", e);
        }
        return null;
    }

    /**
     * 字节数组 转 指定类的对象
     *
     * @param src
     *      Json字符串
     * @param clazz
     *      指定类的类信息
     * @param <T>
     *      Object类型
     * @return T
     *      指定类的对象
     */
    public static <T> T parseObject(final byte[] src, final Class<T> clazz) {
        if (src == null) {
            return null;
        }
        final String json = new String(src, UTF_8);
        return parseObject(json, clazz);
    }

    /**
     * Json字符串 转 List
     *
     * @param json
     *      Json字符串
     * @param clazz
     *      指定类的类信息
     * @param <T>
     *      Object类型
     * @return List<T>
     *      指定类的List对象
     */
    public static <T> List<T> toList(final String json, final Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return Collections.emptyList();
        }

        try {

            final CollectionType listType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            return OBJECT_MAPPER.readValue(json, listType);
        } catch (Exception e) {
            log.error("parse list exception!", e);
        }

        return Collections.emptyList();
    }

    /**
     * 检查Json字符串有效性
     *
     * @param json
     *      Json字符串
     * @return boolean
     *      true：有效
     *      false：无效
     */
    public static boolean checkJsonValid(final String json) {

        if (StringUtils.isEmpty(json)) {
            return false;
        }

        try {
            OBJECT_MAPPER.readTree(json);
            return true;
        } catch (IOException e) {
            log.error("check json object valid exception!", e);
        }

        return false;
    }

    /**
     * 在当前节点或者子节点，通过字段名称查找字段值
     * 如果找到，则返回字段值
     * 如果没找到，则返回null
     *
     * @param jsonNode
     *      Json节点
     * @param fieldName
     *      字段名称
     * @return String
     *      字段值 或 null
     */
    public static String findValue(final JsonNode jsonNode, final String fieldName) {
        final JsonNode node = jsonNode.findValue(fieldName);

        if (node == null) {
            return null;
        }

        return node.toString();
    }

    /**
     * Json字符串 转 Map对象
     *
     * @param json
     *      Json字符串
     * @return Map<String, String>
     *     Map对象
     */
    public static Map<String, String> toMap(final String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<Map<String, String>>() {
            });
        } catch (Exception e) {
            log.error("json to map exception!", e);
        }

        return null;
    }

    /**
     * Json字符串 转 Map对象
     *
     * @param json
     *      Json字符串
     * @param classK
     *      key的类信息
     * @param classV
     *          value的类信息
     * @param <K> K
     *      Object类型K
     * @param <V> V
     *      Object类型V
     * @return Map<K, V>
     *      指定KV类型的Map对象
     */
    public static <K, V> Map<K, V> toMap(final String json, final Class<K> classK, final Class<V> classV) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<Map<K, V>>() {
            });
        } catch (Exception e) {
            log.error("json to map exception!", e);
        }

        return null;
    }

    /**
     * Object对象 转 Json字符串
     *
     * @param object
     *      Object对象
     * @return String
     *      Json字符串
     */
    public static String toJsonString(final Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Object json deserialization exception.", e);
        }
    }

    /**
     * Object对象 转 Json字节数组
     *
     * @param obj
     *      Object对象
     * @param <T>
     *      Object类型
     * @return byte[]
     *      Json字节数组
     */
    public static <T> byte[] toJsonByteArray(final T obj)  {
        if (obj == null) {
            return null;
        }
        String json = "";
        try {
            json = toJsonString(obj);
        } catch (Exception e) {
            log.error("json serialize exception.", e);
        }

        return json.getBytes(UTF_8);
    }

    /**
     * 字符串 转 Object节点
     *
     * @param text
     *      字符串
     * @return ObjectNode
     *      Object节点
     */
    public static ObjectNode parseObject(final String text) {
        try {
            return (ObjectNode) OBJECT_MAPPER.readTree(text);
        } catch (Exception e) {
            throw new RuntimeException("String json deserialization exception.", e);
        }
    }

    /**
     * 字符串 转 Array节点
     *
     * @param text
     *      字符串
     * @return ArrayNode
     *      Array节点
     */
    public static ArrayNode parseArray(final String text) {
        try {
            return (ArrayNode) OBJECT_MAPPER.readTree(text);
        } catch (Exception e) {
            throw new RuntimeException("Json deserialization exception.", e);
        }
    }

    /**
     * 内部静态类：Json数据序列化
     */
    public static class JsonDataSerializer extends JsonSerializer<String> {

        @Override
        public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeRawValue(value);
        }

    }

    /**
     * 内部静态类：Json数据反序列化
     */
    public static class JsonDataDeserializer extends JsonDeserializer<String> {

        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            if (node instanceof TextNode) {
                return node.asText();
            } else {
                return node.toString();
            }
        }

    }
}
