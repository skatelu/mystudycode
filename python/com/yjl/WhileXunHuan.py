# While 循环
#  for 循环用于针对集合中的每个元素都一个代码块，而while循环不断地运行，直到制定的条件不满足为止
count_number=1
while count_number <= 5:
    print(count_number)
    count_number += 1
# 可使用while 循环让程序在用户愿意时不断地运行， 如下面的程序parrot.py所示。 我们在其中定义了一个退出值， 只要用户输入的不是这个值， 程序就接着运行：
prompt = "\nTell me something, and I will repeat it back to you:"
prompt += "\nEnter 'quit' to end the program."
message=''
while message != "quit":
    message = input(prompt)
    if message!="quit":
        print(message)

# 使用back退出循环
# 要立即退出while 循环， 不再运行循环中余下的代码， 也不管条件测试的结果如何， 可使用break 语句。
prompt = "\nPlease enter the name of a city you have visited:"
prompt += "\n(Enter 'quit' when you are finished.) "

while True:
    city = input(prompt)
    if city == 'quit':
        break
    else:
        print("I'd love to go to " + city.title() + "!")


