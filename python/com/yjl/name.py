# 字符串相关操作
# 修改字符串的大小写  title() 以首字母大写的方式显示每个单词，即将每个单词的首字母都改为大写。
name = 'ada lovelace'
print(name.title())

# 其它几个很有用的大小写处理方法   要将字符串改为全部大写或全部小写
print(name.upper()) #将字符串转换成大写
print(name.lower()) #将字符串转成小写

# 合并（拼接）字符串
frist_name = 'ada'
last_name = 'lovelace'
full_name = frist_name + " " + last_name
print(full_name + '拼接字符串')

# 使用制表符或换行符来添加空白  要在字符串中添加制表符，可使用字符组合\t
print("\tPython")

# 要在字符串中添加换行符，可使用字符组合\n
print("Languages:\nPython\nC\nJavaScript")

# 还可在同一个字符串中同时包含制表符和换行符。字符串"\n\t" 让Python换到下一行，并在下一行开头添加一个制表符。
# 下面的示例演示了如何使用一个单行字符串来生成四行输出
print("Languages:\n\tPython\n\tC\n\tJavaScript")

# 删除空白  即删除字符串两端的空格
# Python能够找出字符串开头和末尾多余的空白。要确保字符串末尾没有空白，可使用方法rstrip()
# 要永久删除这个字符串中的空白，必须将删除操作的结果存回到变量中
favorite_language = 'python '
favorite_language1 = favorite_language.rstrip()
print(favorite_language+favorite_language1)
# 剔除字符串开头的空白，或同时剔除字符串两端的空白。
favorite_language2 = ' python2 '
# 提出开头空白
favorite_language3 = favorite_language2.lstrip()
# 提出两端空白
favorite_language4 = favorite_language2.strip()
print(favorite_language3+favorite_language4)

#将int类型的转换成字符串类型  str() 函数
age = 23
message = "Happy " + str(age) + "rd Brithday!"
print(message)

#浮点类型数数据
print(3.0/2)

import this











