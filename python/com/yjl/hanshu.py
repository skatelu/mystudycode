# 函数input（） 的工作原理
#  函数input() 让程序暂听运行，的古代用户输入一些文本。获取用户输入后，Python将其存储在一个变量中，以方便你时候
#  函数input（） 会接受一个参数： 即要向用户显示的提示或说明，让用户知道该如何做
message = input("Tell me someThing, and I will repeat it back to you: ")
print(message)


height = input("How tall are you, in inches? ")
height = int(height)

if height >= 36:
    print("\nYou're tall then enought to ride!")
else:
    print("\nYou'll be able to ride when you're a little older.")

# 求模运算符  %  它将两个数相除并返回余数
print(4 % 3)


