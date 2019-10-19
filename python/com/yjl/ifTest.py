cars = ['dazhong','bengchi','baoma','toyota']
for car in cars:
    if car == 'dazhong':
        print(car.upper())
    else:
        print(car.title())

# 在Python中检查相等时区分大小写
car1 = 'Audi'
print(car1 == 'audi')

# if-elif-else结构
age = 12
if age <= 4:
    print('Your admission cost is $0.')
elif age <= 12:
    print('Your admission cost is $0.')
else:
    print('Your admission cost is $10.')

