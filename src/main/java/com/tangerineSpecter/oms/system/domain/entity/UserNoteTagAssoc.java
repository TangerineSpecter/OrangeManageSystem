package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 卡片笔记标签关联表
 *
 * @author TangerineSpecter
 * @version v0.5.0
 * @Date 2022年01月21日17:42:53
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_note_tag_assoc")
public class UserNoteTagAssoc implements Serializable {
	
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 笔记id
	 */
	private String noteId;
	/**
	 * 标签id
	 */
	private Long tagId;
}
