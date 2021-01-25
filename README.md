# 数据服务接口

### 1. 接口信息
`URL结构   http://localhost:8888/api/v1/...`

### 2. 目录信息
```
base            基础设施包
config          配置信息包
controller      资源控制包
dto             参数封装包
entity          实体包
enums           枚举包
exception       异常包
mapper          映射包
service         服务包
util            工具包
```

### 3. Swagger调试信息
`http://localhost:8888/swagger-ui.html`

### 4. Swagger的注解
```
1. @Api注解可以用来标记当前Controller的功能。
2. @ApiOperation注解用来标记一个方法的作用。
3. @ApiImplicitParam注解用来描述一个参数，可以配置参数的中文含义，也可以给参数设置默认值，这样在接口测试的时候可以避免手动输入。
4. 如果有多个参数，则需要使用多个@ApiImplicitParam注解来描述，多个@ApiImplicitParam注解需要放在一个@ApiImplicitParams注解中。
5. 需要注意的是，@ApiImplicitParam注解中虽然可以指定参数是必填的，但是却不能代替@RequestParam(required = true)，前者的必填只是在Swagger2框架内必填，抛弃了Swagger2，这个限制就没用了，所以假如开发者需要指定一个参数必填，@RequestParam(required = true)注解还是不能省略。
6. 如果参数是一个对象（例如上文的更新接口），对于参数的描述也可以放在实体类中。
```

### 5. token认证
```
1. 请求头中的token名称：X-Access-Token
2. 对外提供common权限的通用token：eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTQyMjAxMzcsInVzZXJJZCI6IjEwMDAyIn0.qKNOxWLO_MJS38EZduaVAqDUd1mDNvhLUPJcboA_IPo
3. 用户注册流程：
    使用主用户（admin）的token访问http://localhost:8888/sys/v1/user/register接口
    示例：
    a. 请求头：
    X-Access-Token:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTQxNjMwODYsInVzZXJJZCI6IjEwMDAxIn0.JUgmfgDYqWLoLOObDvy79rJOBJqPUtmrY6mMaFH01eQ
    b. 请求体：
    {
      "password": "string",
      "remark": "string",
      "userId": "string",
      "userName": "string"
    }
```