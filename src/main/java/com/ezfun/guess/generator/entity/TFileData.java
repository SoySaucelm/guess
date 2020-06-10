package com.ezfun.guess.generator.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author SoySauce
 * @since 2019-11-14
 * @version 1.0
 */
@Table(name = "t_file_data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TFileData implements Serializable {
    /**
     * id
     */
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 文件名
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件路径
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * 文件扩展名
     */
    @Column(name = "file_ext")
    private String fileExt;

    /**
     * 文件大小
     */
    @Column(name = "file_size")
    private Integer fileSize;

    /**
     * hash
     */
    private String hash;

    /**
     * 上传时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 上传人
     */
    @Column(name = "user_id")
    private Long userId;

    private static final long serialVersionUID = 1L;
}