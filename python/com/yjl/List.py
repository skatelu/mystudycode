# 列表 其实就是java中数组
# 列表 由一系列按特定顺序排列的元素组成。也可以将任何东西加入列表中，其中的元素之间可以没有任何关系
bicycles = ['tick','cannondale','redline','specialized']
print(bicycles)

# 访问列表元素 列表是有序集合，因此要访问列表的任何元素，只需将该元素的位置或索引告诉Python即可
print(bicycles[1])

# Python为访问最后一个列表元素提供了一种特殊语法。通过将索引指定为-1 ，可让Python返回最后一个列表元素
print(bicycles[-1])

# 修改列表元素的值 就是给列表中的数据重新赋值
bicycles[1] = 'nihao'
print(bicycles)

#在列表中添加元素
bicycles.append('ducati')
print(bicycles)

# 使用方法insert() 可在列表的任何位置添加新元素。为此，你需要指定新元素的索引和值。
bicycles.insert(0,'china')
print(bicycles)

# 从列表中删除数据
# 使用del 语句删除元素，使用del 可删除任何位置处的列表元素，条件是知道其索引。
del bicycles[0]
print(bicycles)

# 方法pop() 可删除列表末尾的元素，并让你能够接着使用它。术语弹出 （pop）源自这样的类比：列表就像一个栈，而删除列表末尾的元素相当于弹出栈顶元素。
popped_motorcycle = bicycles.pop()
print(bicycles)
print(popped_motorcycle)
# 也可以让pop弹出任意索引位置的元素
frist_owned = bicycles.pop(0)
print(frist_owned)

# 根据值删除元素
bicycles.remove("nihao")
print(bicycles)

# 组织列表
# Python中的sort方法 使用方法sort() 对列表进行永久性排序,无法恢复到原来的排序
cars = ['bmw','audi','toyota','subaru']
cars.sort()
print(cars)

# 你还可以按与字母顺序相反的顺序排列列表元素，为此，只需向sort() 方法传递参数reverse=True 。
cars.sort(reverse=True)
print(cars)

# 使用函数sorted() 对列表进行临时排序
# 函数sorted() 让你能够按特定顺序显示列表元素，同时不影响它们在列表中的原始排列顺序。
print(sorted(cars))

# 倒着打印列表
# 要反转列表元素的排列顺序， 可使用方法reverse() 。
cars.reverse()
print(cars)

# 确定列表的长度
# 使用函数len() 可快速获悉列表的长度。
print(len(cars))

# 循环遍历数组
magicians = ['alice','david','carolina']
for bicycle in magicians:
    print(bicycle)
for item in magicians:
    print(item.title()+",that was a great trick!")

# range 函数
for value in  range(1,6):
    print(value)

# list（） 函数
numbers = list(range(1,6))
print(numbers)

# range函数中 ** 表示乘方操作
squares = []
for value in range(1,11):
    square = value**2
    squares.append(square);

print(squares)

squares=[value**2 for value in range(1,11)]
print(squares)

# 复制列表：
my_squares = squares[:]
print(my_squares)

# 定义元组：
dimesions = (200,50)
print(dimesions[1])
print(dimesions[0])

dimesions[1] = 600#元组中的数据不能被修改，元组可以重新被赋值
print(dimesions)