package app.transfer.model

import java.sql.Timestamp

data class Transfer(
	val id: Int,
	val payee: String,
	val receiver: String,
	val amount: Double,
	val time: Timestamp,
	val success: Boolean?
)