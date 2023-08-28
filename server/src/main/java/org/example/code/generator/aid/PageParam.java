package org.example.code.generator.aid;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageParam<T>  implements Serializable{
	
	private static final long serialVersionUID = -7248374800878487522L;
	/**
     * <p>当前页</p>
     */
    private int pageNum=1;
    /**
     * <p>每页记录数</p>
     */
    private int pageSize=10;

    /**
     * <p>分页外的其它参数</p>
     */
    private T param;
    
}
