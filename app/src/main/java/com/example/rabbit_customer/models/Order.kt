package com.example.rabbit_customer.models

class Order(
	var id: String,
	var uid: String,
	var start: String,
	var senderPhone: String,
	var receiverPhone: String,
	var startLatlng: String,
	var endLatlng: String,
	var end: String,
	var size: String,
	var img: String,
	var remarks: String,
	var driverId: String? = null,
	var status: Int = 0    //0 send 1 被接單 2 已完成

)
