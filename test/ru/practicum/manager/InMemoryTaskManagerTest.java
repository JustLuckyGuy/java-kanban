package ru.practicum.manager;


public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{

    @Override
    protected InMemoryTaskManager createTask() {
        return new InMemoryTaskManager();
    }

}
