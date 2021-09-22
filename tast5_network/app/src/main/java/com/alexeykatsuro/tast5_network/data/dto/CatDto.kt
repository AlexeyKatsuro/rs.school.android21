package com.alexeykatsuro.tast5_network.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatDto(
    @SerialName("breeds")
    val breeds: List<Breed>?,
    @SerialName("id")
    val id: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("width")
    val width: Int?,
    @SerialName("height")
    val height: Int?
) {
    @Serializable
    data class Breed(
        @SerialName("weight")
        val weight: Weight?,
        @SerialName("id")
        val id: String?,
        @SerialName("name")
        val name: String?,
        @SerialName("cfa_url")
        val cfaUrl: String?,
        @SerialName("vetstreet_url")
        val vetstreetUrl: String?,
        @SerialName("vcahospitals_url")
        val vcahospitalsUrl: String?,
        @SerialName("temperament")
        val temperament: String?,
        @SerialName("origin")
        val origin: String?,
        @SerialName("country_codes")
        val countryCodes: String?,
        @SerialName("country_code")
        val countryCode: String?,
        @SerialName("description")
        val description: String?,
        @SerialName("life_span")
        val lifeSpan: String?,
        @SerialName("indoor")
        val indoor: Int?,
        @SerialName("alt_names")
        val altNames: String?,
        @SerialName("adaptability")
        val adaptability: Int?,
        @SerialName("affection_level")
        val affectionLevel: Int?,
        @SerialName("child_friendly")
        val childFriendly: Int?,
        @SerialName("dog_friendly")
        val dogFriendly: Int?,
        @SerialName("energy_level")
        val energyLevel: Int?,
        @SerialName("grooming")
        val grooming: Int?,
        @SerialName("health_issues")
        val healthIssues: Int?,
        @SerialName("intelligence")
        val intelligence: Int?,
        @SerialName("shedding_level")
        val sheddingLevel: Int?,
        @SerialName("social_needs")
        val socialNeeds: Int?,
        @SerialName("stranger_friendly")
        val strangerFriendly: Int?,
        @SerialName("vocalisation")
        val vocalisation: Int?,
        @SerialName("experimental")
        val experimental: Int?,
        @SerialName("hairless")
        val hairless: Int?,
        @SerialName("natural")
        val natural: Int?,
        @SerialName("rare")
        val rare: Int?,
        @SerialName("rex")
        val rex: Int?,
        @SerialName("suppressed_tail")
        val suppressedTail: Int?,
        @SerialName("short_legs")
        val shortLegs: Int?,
        @SerialName("wikipedia_url")
        val wikipediaUrl: String?,
        @SerialName("hypoallergenic")
        val hypoallergenic: Int?,
        @SerialName("reference_image_id")
        val referenceImageId: String?
    ) {
        @Serializable
        data class Weight(
            @SerialName("imperial")
            val imperial: String?,
            @SerialName("metric")
            val metric: String?
        )
    }
}