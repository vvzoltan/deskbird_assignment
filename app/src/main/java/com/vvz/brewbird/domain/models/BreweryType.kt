package com.vvz.brewbird.domain.models

enum class BreweryType {
    micro,      // Most craft breweries. For example, Samual Adams is still considered a micro brewery.
    nano,       // An extremely small brewery which typically only distributes locally.
    regional,   // A regional location of an expanded brewery. Ex. Sierra Nevada’s Asheville, NC location.
    brewpub,    // A beer-focused restaurant or restaurant/bar with a brewery on-premise.
    large,      // A very large brewery. Likely not for visitors. Ex. Miller-Coors. (deprecated)
    planning,   // A brewery in planning or not yet opened to the public.
    bar,        // A bar. No brewery equipment on premise. (deprecated)
    contract,   // A brewery that uses another brewery’s equipment.
    proprietor, // Similar to contract brewing but refers more to a brewery incubator.
    closed,
    unknown;

    companion object {
        fun fromName(name: String): BreweryType {
            return BreweryType.values().find { it.name == name } ?: unknown
        }
    }
}