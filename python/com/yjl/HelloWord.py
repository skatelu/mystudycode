print("Hello =================== World")
# input 函数  将括号内的数据打印出来，并要求输入一个内容  将这个内容赋值给其他变量
temp = input("不妨猜一下，我心里想的是哪个数字")
# int() 进行类型转换  转换成int类型  进行赋值
guess = int(temp)
if guess == 8:
    print("哇，你是我的蛔虫么")
    print("哼，猜中了也没奖励")
else:
    print("猜错了，我心里想的是8")
print("游戏结束了，不玩了")

#BIF = Built-in function 内置函数

