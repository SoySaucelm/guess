# coding=utf-8
from selenium import webdriver


# 访问百度首页
first_url = 'https://www.baidu.com/'
# print("now access %s" % (first_url))

quit()
driver = webdriver.Chrome()
driver.get(first_url)
driver.find_element_by_xpath("//*").get_attribute("outerHTML")
print(driver.get_cookies())
# print(html)
driver.close()
driver.quit();
print("hello")
x = 100
print(x)
a={"a":"22222"}
print(a)
def normalize(name):
    return name[0].upper() + name[1:].lower()

L1 = ['adam', 'LISA', 'barT']
L2 = list(map(lambda name: name[0].upper() + name[1:].lower(), L1))
print(L2)
quit()

