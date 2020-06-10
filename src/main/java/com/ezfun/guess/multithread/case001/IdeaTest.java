package com.ezfun.guess.multithread.case001;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author SoySauce
 * @date 2019/9/10
 */
public class IdeaTest {
    @Autowired
    private GoodSuspend goodSuspend;

    public GoodSuspend getGoodSuspend1(GoodSuspend goodSuspend) {
        return goodSuspend;
    }

    public GoodSuspend getGoodSuspend2(GoodSuspend goodSuspend) {
        return goodSuspend;
    }
//    上面的代码中，有5个地方用到了goodSuspend文本，如何批量修改呢？
// 首先是使用ctrl+w选中rabbitTemplate这个文本,然后依次使用5次alt+j快捷键，逐个选中，这样五个文本就都被选中并且高亮起来了，这个时候就可以直接批量修改了。

    public static void main(String[] args) {
        //language=html
        String html = "<div>\n" +
                "    <span>hello</span>\n" +
                "</div>\n";
        if (html == null) {

        }
    }
}
