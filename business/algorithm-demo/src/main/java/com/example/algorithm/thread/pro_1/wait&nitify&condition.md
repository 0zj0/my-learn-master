condition 与 wait、notify 区别
（1）Object的一个对象只能又一个等待队列，而针对Lock，一个对象可以有多个等待等待队列。

（2）使用Lock比使用Object加锁的方式具有更好的灵活性。可以响应中断和设置获取锁的超时间。

（3）Lock支持公平性和非公平性的设置，而synchronized不支持。