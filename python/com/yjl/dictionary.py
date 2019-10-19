# 字典
# 在Python中，字典是一系列的 键—值 对。相当于java中Map
alien_0 = {'color':'green','points':5}

print(alien_0['color'])
print(alien_0.keys())

# 添加 键—值 对
alien_0['woshi'] = 'yangjunlu'
alien_0['xing'] = 'yang'

print(alien_0)

# 修改字典中的值
alien_0['color'] = 'red'
print(alien_0)

# 遍历字典： 使用数组.item() 方法
for key,value in alien_0.items():
    print("\nKey: " + key)
    print("Value: " + str(value))

# 在字典中存储列表
pizza = {
    'crust':'thick',
    'toppings':['mushrooms','extra cheese']
}
# 概述所点的比萨
print("You ordered a " + pizza['crust'] + "-crust pizza" + "")