<table class="table table-bordered table-striped table-condensed">
    <tr>
        <td>a</td>
	<td>b</td>
    </tr>
    <tr>
        <td>c</td>
	<td>d</td>
    </tr>
</table>

| Tables        | Are           | Cool  |
| ------------- |:-------------:| -----:|
| col 3 is      | right-aligned | $1600 |
| col 2 is      | centered      |   $12 |
| zebra stripes | are neat      |    $1 |

项目     | 价格
---      | ---
Computer | $1600
Phone    | $12
Pipe     | $1


项目     |  
---      | 
Computer | 
Phone    | 
Pipe     | 

> 算法
>> 排序算法
#### `插入排序`


增量|  数组 |tmp
---|:---:|:---:
0|5 52 6  3  4|
1|5 52 6  3  4 |    52
>|5 52 52 3  4 |    6
2|5 6  52 3  4|
3|5 6  52 52 4 |    3
>|5 6  6  52 4|
>|5 5  6  52 4|
4|3 5  6  52 4|

#### `希尔排序`
```java
//最大值增量 
class Sort{
void shellSort(int[] arr){
   //计算出最大的h值
          int h = 1;
          while (h <= arr.length / 3) {
              h = h * 3 + 1;
          }
          while (h > 0) { //必须大于0
              for (int i = h; i < arr.length; i++) {
                  if (arr[i] < arr[i - h]) {
                      int tmp = arr[i];
                      int j = i - h;
                      while (j >= 0 && arr[j] > tmp) {
                          arr[j + h] = arr[j];
                          j -= h;
                      }
                      arr[j + h] = tmp;
                  }
              }
              //计算出下一个h值
              h = (h - 1) / 3;
           } 
    }
}

```


增量|  数组|>|>|>|>|tmp|
---|:---:|:---:|:---:|:---:|:---:|:---:|
>|5  |52  |6  |3  |4|  > |
4|5  |>   |>  |>  |4|      tmp=4  
>|5  |>   |>  |>  |5|         插入 
>|4  |>   |>  |>  |5|       赋值=tmp
1 4  52                     tmp=6
>|    52  6
>|   52  52                 插入
>|   6   52
>|       52  3              tmp=3
>|       52  52
>|   6   52                 6>3
>|   6   6        
>|4  6                      4>3
>|4  4 
>|3  4 
>|           52  5          tmp=5
>|           52  52         52>5
>|      6    52             6>5
>|      6    6
>|   4  6                   4<5 不成立break
>|      5    6
>|3  4  5    6   52         最终结果    
        
        
               