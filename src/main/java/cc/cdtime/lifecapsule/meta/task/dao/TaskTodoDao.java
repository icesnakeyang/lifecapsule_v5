package cc.cdtime.lifecapsule.meta.task.dao;

import cc.cdtime.lifecapsule.meta.task.entity.TaskTodo;
import cc.cdtime.lifecapsule.meta.task.entity.TaskView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface TaskTodoDao {
    /**
     * 创建一个todo任务
     *
     * @param taskTodo
     */
    void createTaskTodo(TaskTodo taskTodo);

    /**
     * 读取todo任务列表
     *
     * @param qIn userId
     *            noteId
     *            complete
     *            projectId
     *            size
     *            offset
     * @return
     */
    ArrayList<TaskView> listTaskTodo(Map qIn);

    /**
     * 统计todo任务总数
     *
     * @param qIn userId
     * @return
     */
    Integer totalTaskTodo(Map qIn);

    /**
     * 读取一个todo任务详情
     *
     * @param taskId
     * @return
     */
    TaskView getTaskTodo(String taskId);

    /**
     * 修改todo任务
     *
     * @param qIn title
     *            priority
     *            complete
     *            projectId
     */
    void updateTaskTodo(Map qIn);

    /**
     * 物理删除一个todo任务
     *
     * @param qIn taskId
     *            noteId
     */
    void deleteTaskTodo(Map qIn);
}
