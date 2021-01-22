# 数据服务接口

### 1. 接口信息
`URL结构   http://localhost:8888/api/v1/...`

### 2. 目录信息
```
config          配置信息类
controller      资源控制类
domain          实体类
enums           枚举类
mapper          映射类
service         服务类
util            工具类
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