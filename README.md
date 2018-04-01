# minijava

# Typecheck
# 1. 符号表
1. 类的继承关系树
2. 类的成员变量和方法
3. 方法的参数和返回类型
4. 方法的局部变量

# 2. 类型检查
1. 使用未定义的类、变量和方法
2. 重复定义类、变量和方法    ps:在符号表中做
3. 类型不匹配
• if、while的判断表达式必须是boolean型
• Print参数必须为整数
• 数组下标必须是int型
• 赋值表达式左右操作数类型匹配
4. 参数不匹配
• 类型、个数、return语句返回类型
• 不允许重载
5. 操作数相关：加、乘、小于等操作数须为整数
6. 类的循环继承、多重继承
7. 数组越界
8. 使用未初始化的变量
