package no.jinil.utils.smartlog

data class SampleData(
    val users: List<SampleUser>
)

data class SampleUser(
    val id: Int,
    val name: String,
)

val sampleData = SampleData(
    users = listOf(
        SampleUser(id = 0, name = "철수"),
        SampleUser(id = 1, name = "영희"),
    )
)