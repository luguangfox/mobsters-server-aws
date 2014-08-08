package com.lvl6.mobsters.info;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity(name="Task")
@Table(name="task")
@Proxy(lazy=true, proxyClass=ITask.class)
public class Task extends BaseIntPersistentObject implements ITask{

	private static final long serialVersionUID = -5841712902617988030L;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@OneToOne(fetch=FetchType.LAZY, targetEntity=Task.class, optional=true)
	@JoinColumn(
		name = "prerequisite_task_id",
		nullable = true,
		foreignKey=@ForeignKey(name="none", value=ConstraintMode.NO_CONSTRAINT))
	private ITask prerequisiteTask;

	@Column(name = "board_width")
	private int boardWidth;
	
	@Column(name = "board_height")
	private int boardHeight;
	
	@OneToMany(
		cascade={CascadeType.PERSIST, CascadeType.REFRESH},
		fetch=FetchType.EAGER,
		mappedBy="task", 
		orphanRemoval=true,
		targetEntity=TaskStage.class)
	@OrderBy("level ASC")
	private List<ITaskStage> taskStages;
	
	public Task() {
		super();
		this.taskStages = new ArrayList<ITaskStage>(3);
	}

	public Task(final int id, final String name, final String description,
			final ITask prerequisiteTask, final int boardWidth,
			final int boardHeight, List<ITaskStage> taskStages)
	{
		super(id);
		this.name = name;
		this.description = description;
		this.prerequisiteTask = prerequisiteTask;
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		if( taskStages == null ) {
			this.taskStages = new ArrayList<ITaskStage>(3);
		} else {
			this.taskStages = taskStages;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lvl6.mobsters.info.ITask#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see com.lvl6.mobsters.info.ITask#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see com.lvl6.mobsters.info.ITask#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}
	/* (non-Javadoc)
	 * @see com.lvl6.mobsters.info.ITask#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	/* (non-Javadoc)
	 * @see com.lvl6.mobsters.info.ITask#getPrerequisiteTask()
	 */
	@Override
	public ITask getPrerequisiteTask() {
		return prerequisiteTask;
	}
	/* (non-Javadoc)
	 * @see com.lvl6.mobsters.info.ITask#setPrerequisiteTask(com.lvl6.mobsters.info.ITask)
	 */
	@Override
	public void setPrerequisiteTask(ITask prerequisiteTask) {
		this.prerequisiteTask = prerequisiteTask;
	}

	@Override
	public int getBoardWidth()
	{
		return boardWidth;
	}

	@Override
	public void setBoardWidth( int boardWidth )
	{
		this.boardWidth = boardWidth;
	}

	@Override
	public int getBoardHeight()
	{
		return boardHeight;
	}

	@Override
	public void setBoardHeight( int boardHeight )
	{
		this.boardHeight = boardHeight;
	}
	
	@Override
	public List<ITaskStage> getTaskStages() {
		return this.taskStages;
	}

	@Override
	public String toString()
	{
		return "Task [name="
			+ name
			+ ", description="
			+ description
			+ ", prerequisiteTask="
			+ prerequisiteTask
			+ ", boardWidth="
			+ boardWidth
			+ ", boardHeight="
			+ boardHeight
			+ ", taskStages="
			+ taskStages.toString()
			+ "]";
	}
}
