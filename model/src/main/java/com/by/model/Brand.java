package com.by.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.by.json.BrandJson;

@Entity
@Table(name = "by_brand")
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// 品牌名称
	private String name;
	//品牌状态(‘0’代表正常，‘1’代表冻结)
		private  Integer status;
	// 全部页面排序号
	@Column(name = "sort")
	private Integer sortAll;
	// 业态页面排序号
	@Column(name = "sort2")
	private Integer sort;
	// 品牌推荐图
	@Column(name = "special_img")
	private String specialImg;
	// 品牌大图
	@Column(name = "brand_img")
	private String bigImg;
	// 品牌小图
	@Column(name = "brand_img2")
	private String smallImg;
	// 品牌位置
	private String address;
	// 业态ShopCategory
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brand_category_id")
	private ShopCategory brandCategory;
	// 全部页面是否显示大图
	@Column(name = "is_big_img")
	private Integer isAllBigImg;
	// 业态页面是否显示大图
	@Column(name = "is_big_img2")
	private Integer isBigImg;

	private String created_by;
	private String updated_by;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar created_time;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar updated_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSortAll() {
		return sortAll;
	}
	public void setSortAll(Integer sortAll) {
		this.sortAll = sortAll;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getBigImg() {
		return bigImg;
	}
	public void setBigImg(String bigImg) {
		this.bigImg = bigImg;
	}
	public String getSmallImg() {
		return smallImg;
	}
	public void setSmallImg(String smallImg) {
		this.smallImg = smallImg;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public ShopCategory getBrandCategory() {
		return brandCategory;
	}
	public void setBrandCategory(ShopCategory brandCategory) {
		this.brandCategory = brandCategory;
	}
	public Integer getIsBigImg() {
		return isBigImg;
	}
	public void setIsBigImg(Integer isBigImg) {
		this.isBigImg = isBigImg;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public Calendar getCreated_time() {
		return created_time;
	}
	public void setCreated_time(Calendar created_time) {
		this.created_time = created_time;
	}
	public Calendar getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(Calendar updated_time) {
		this.updated_time = updated_time;
	}
	public String getSpecialImg() {
		return specialImg;
	}
	public void setSpecialImg(String specialImg) {
		this.specialImg = specialImg;
	}
	public Integer getIsAllBigImg() {
		return isAllBigImg;
	}
	public void setIsAllBigImg(Integer isAllBigImg) {
		this.isAllBigImg = isAllBigImg;
	}
	public BrandJson toJson() {
		return new BrandJson(this);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((bigImg == null) ? 0 : bigImg.hashCode());
		result = prime * result + ((brandCategory == null) ? 0 : brandCategory.hashCode());
		result = prime * result + ((created_by == null) ? 0 : created_by.hashCode());
		result = prime * result + ((created_time == null) ? 0 : created_time.hashCode());
		result = prime * result + id;
		result = prime * result + ((isBigImg == null) ? 0 : isBigImg.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((smallImg == null) ? 0 : smallImg.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		result = prime * result + ((sortAll == null) ? 0 : sortAll.hashCode());
		result = prime * result + ((specialImg == null) ? 0 : specialImg.hashCode());
		result = prime * result + ((updated_by == null) ? 0 : updated_by.hashCode());
		result = prime * result + ((updated_time == null) ? 0 : updated_time.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Brand other = (Brand) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (bigImg == null) {
			if (other.bigImg != null)
				return false;
		} else if (!bigImg.equals(other.bigImg))
			return false;
		if (brandCategory == null) {
			if (other.brandCategory != null)
				return false;
		} else if (!brandCategory.equals(other.brandCategory))
			return false;
		if (created_by == null) {
			if (other.created_by != null)
				return false;
		} else if (!created_by.equals(other.created_by))
			return false;
		if (created_time == null) {
			if (other.created_time != null)
				return false;
		} else if (!created_time.equals(other.created_time))
			return false;
		if (id != other.id)
			return false;
		if (isBigImg == null) {
			if (other.isBigImg != null)
				return false;
		} else if (!isBigImg.equals(other.isBigImg))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (smallImg == null) {
			if (other.smallImg != null)
				return false;
		} else if (!smallImg.equals(other.smallImg))
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		if (sortAll == null) {
			if (other.sortAll != null)
				return false;
		} else if (!sortAll.equals(other.sortAll))
			return false;
		if (specialImg == null) {
			if (other.specialImg != null)
				return false;
		} else if (!specialImg.equals(other.specialImg))
			return false;
		if (updated_by == null) {
			if (other.updated_by != null)
				return false;
		} else if (!updated_by.equals(other.updated_by))
			return false;
		if (updated_time == null) {
			if (other.updated_time != null)
				return false;
		} else if (!updated_time.equals(other.updated_time))
			return false;
		return true;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
