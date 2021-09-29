package com.alexeykatsuro.tast5_network.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatDto(
    @SerialName("breeds")
    val breeds: List<Breed> = emptyList(),
    @SerialName("id")
    val id: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("width")
    val width: Int? = null,
    @SerialName("height")
    val height: Int? = null
) {
    @Serializable
    data class Breed(
        @SerialName("weight")
        val weight: Weight? = null,
        @SerialName("id")
        val id: String? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("cfa_url")
        val cfaUrl: String? = null,
        @SerialName("vetstreet_url")
        val vetstreetUrl: String? = null,
        @SerialName("vcahospitals_url")
        val vcahospitalsUrl: String? = null,
        @SerialName("temperament")
        val temperament: String? = null,
        @SerialName("origin")
        val origin: String? = null,
        @SerialName("country_codes")
        val countryCodes: String? = null,
        @SerialName("country_code")
        val countryCode: String? = null,
        @SerialName("description")
        val description: String? = null,
        @SerialName("life_span")
        val lifeSpan: String? = null,
        @SerialName("indoor")
        val indoor: Int? = null,
        @SerialName("alt_names")
        val altNames: String? = null,
        @SerialName("adaptability")
        val adaptability: Int? = null,
        @SerialName("affection_level")
        val affectionLevel: Int? = null,
        @SerialName("child_friendly")
        val childFriendly: Int? = null,
        @SerialName("dog_friendly")
        val dogFriendly: Int? = null,
        @SerialName("energy_level")
        val energyLevel: Int? = null,
        @SerialName("grooming")
        val grooming: Int? = null,
        @SerialName("health_issues")
        val healthIssues: Int? = null,
        @SerialName("intelligence")
        val intelligence: Int? = null,
        @SerialName("shedding_level")
        val sheddingLevel: Int? = null,
        @SerialName("social_needs")
        val socialNeeds: Int? = null,
        @SerialName("stranger_friendly")
        val strangerFriendly: Int? = null,
        @SerialName("vocalisation")
        val vocalisation: Int? = null,
        @SerialName("experimental")
        val experimental: Int? = null,
        @SerialName("hairless")
        val hairless: Int? = null,
        @SerialName("natural")
        val natural: Int? = null,
        @SerialName("rare")
        val rare: Int? = null,
        @SerialName("rex")
        val rex: Int? = null,
        @SerialName("suppressed_tail")
        val suppressedTail: Int? = null,
        @SerialName("short_legs")
        val shortLegs: Int? = null,
        @SerialName("wikipedia_url")
        val wikipediaUrl: String? = null,
        @SerialName("hypoallergenic")
        val hypoallergenic: Int? = null,
        @SerialName("reference_image_id")
        val referenceImageId: String? = null
    ) {
        @Serializable
        data class Weight(
            @SerialName("imperial")
            val imperial: String? = null,
            @SerialName("metric")
            val metric: String? = null
        )
    }
}