package com.vzv.shop.entity.constructor;

import com.vzv.shop.data.TestData;
import com.vzv.shop.dto.ProductDTO;
import com.vzv.shop.entity.order.ConstructorOrderLine;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "GLASSES_CONSTRUCTORS")
public class GlassesConstructor {

	@Id
	private String id;

	@ManyToOne(targetEntity = ProductDTO.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "Frame_Id", referencedColumnName = "Id")
	private ProductDTO frame;

	@ManyToOne(targetEntity = ProductDTO.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "OD_Lens_Id", referencedColumnName = "Id")
	private ProductDTO odLens;

	@Column(name = "OD_Angle")
	private String odCylAngle;

	@ManyToOne(targetEntity = ProductDTO.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "OS_Lens_Id", referencedColumnName = "Id")
	private ProductDTO osLens;

	@Column(name = "OS_Angle")
	private String osCylAngle;

	@Column(name = "Distance")
	private String distance;

	@Column(name = "Work_Price")
	private String workPrice;

	@Column(name = "Amount")
	private String quantity;

	@Column(name = "Total_Price")
	private String price;


	public GlassesConstructor(String id, ProductDTO frame, ProductDTO odLens, String odAngle, ProductDTO osLens,
							  String osAngle, String distance, String workPrice, String amount, String price) {
		this.id = id;
		this.frame = frame;
		this.odLens = odLens;
		this.odCylAngle = odAngle;
		this.osLens = osLens;
		this.osCylAngle = osAngle;
		this.distance = distance;
		this.workPrice = TestData.formatPrice(workPrice);
		this.quantity = amount;
		this.price = TestData.formatPrice(price);
	}

	public ConstructorOrderLine toConstrOLine(String orderId){
		return new ConstructorOrderLine(orderId, this, this.quantity, this.price);
	}
}