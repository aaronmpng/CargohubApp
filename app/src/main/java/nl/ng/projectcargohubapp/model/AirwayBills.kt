package nl.ng.projectcargohubapp.model

data class AirwayBills(
    val value : List<AirwayBillsItems>
)

data class AirwayBillsItems(
    var airwayBillID : String,
    var orderID : String,
    var pcs : String,
    var weight : String,
    var volume : String,
    var lm : String,
    var height : String,
    var dgr : String,
    var nature : String
)
