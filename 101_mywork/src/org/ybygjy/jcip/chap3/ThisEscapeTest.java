package org.ybygjy.jcip.chap3;

import java.util.ArrayList;
import java.util.List;

/**
 * 负责this逸出验证
 * @author WangYanCheng
 * @version 2014-7-17
 */
public class ThisEscapeTest {
    public static void main(String[] args) {
        ThisEscape thisEscape = new ThisEscape();
        thisEscape.fireEvent();
    }
    /**
     * 负责构造隐式this引用逸出
     * @author WangYanCheng
     * @version 2014-7-17
     */
    static class ThisEscape implements EventSource {
        /** 内部实例变量*/
        private long price;
        /** 非线程安全事件队列*/
        private List<EventSourceEvent> eventArray = new ArrayList<EventSourceEvent>();
        /**
         * 构造函数
         */
        public ThisEscape() {
            //在构造函数中注册外部事件
            registerListener(new EventSourceEvent(){
                //ThisEscape逸出实例
                private ThisEscape thisEscape;
                /** 注意此时{@link ThisEscape}构造函数并未返回*/
                {
                    System.out.println(thisEscape.eventArray);
                    //不合规法操作_Begin
                    //thisEscape.eventArray = Collections.synchronizedList(new ArrayList<EventSourceEvent>());
                    //thisEscape.price = 0;
                    //不合规法操作_End
                }
                @Override
                public void onEvent(Event e) {
                    thisEscape = ThisEscape.this;
                    doSomething(e);
                    innerDanger(ThisEscape.this);
                }
                public void innerDanger(ThisEscape thisEscape) {
                    thisEscape.doSomething(new Event("VirtualOtherEvent", this));
                }
            });
        }
        @Override
        public String toString() {
            return "ThisEscape [price=" + price + ", eventArray=" + eventArray + "]";
        }
        /**
         * 事件触发
         */
        public void fireEvent() {
            for (EventSourceEvent ese : eventArray) {
                ese.onEvent(new Event("org.ybygjy.jcip.chap3.ThisEscapeTest.ThisEscape.fireEvent()", this));
            }
        }
        /**
         * DoSomething
         * @param event {@link Event}
         */
        public void doSomething(Event event) {
            System.out.println(event.toString());
        }
        @Override
        public void registerListener(EventSourceEvent event) {
            eventArray.add(event);
        }
    }
}
interface EventSource {
    public void registerListener(EventSourceEvent event);
}
interface EventSourceEvent {
    public void onEvent(Event e);
}
class Event {
    private String eventToken;
    private Object srcObj;
    public Event(String eventToken, Object srcObj) {
        this.eventToken = eventToken;
        this.srcObj = srcObj;
    }
    @Override
    public String toString() {
        return "Event [eventToken=" + eventToken + ", srcObj=" + srcObj + "]";
    }
    
}