package com.ego.mapper;

import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Sat Feb 15 18:44:54 CST 2020
     */
    int countByExample(TbOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Sat Feb 15 18:44:54 CST 2020
     */
    int deleteByExample(TbOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Sat Feb 15 18:44:54 CST 2020
     */
    int deleteByPrimaryKey(String orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Sat Feb 15 18:44:54 CST 2020
     */
    int insert(TbOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Sat Feb 15 18:44:54 CST 2020
     */
    int insertSelective(TbOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Sat Feb 15 18:44:54 CST 2020
     */
    List<TbOrder> selectByExample(TbOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Sat Feb 15 18:44:54 CST 2020
     */
    TbOrder selectByPrimaryKey(String orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Sat Feb 15 18:44:54 CST 2020
     */
    int updateByExampleSelective(@Param("record") TbOrder record, @Param("example") TbOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Sat Feb 15 18:44:54 CST 2020
     */
    int updateByExample(@Param("record") TbOrder record, @Param("example") TbOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Sat Feb 15 18:44:54 CST 2020
     */
    int updateByPrimaryKeySelective(TbOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order
     *
     * @mbggenerated Sat Feb 15 18:44:54 CST 2020
     */
    int updateByPrimaryKey(TbOrder record);
}