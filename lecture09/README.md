# Lecture 09 — Android Location-Aware

This lecture covers location-aware Android development. Students learn to retrieve the device's current position using the Fused Location Provider, track location changes in real time with foreground services, and display interactive maps with the Google Maps SDK. Each topic is demonstrated in Jetpack Compose and XML (MDC) variants.

## Apps

| App | Description | UI Framework |
|-----|-------------|--------------|
| [09-1_MyLocation](./09-1_MyLocation) | Real-time location tracking with Fused Location Provider | Jetpack Compose |
| [09-2_MyLocation-MDC](./09-2_MyLocation-MDC) | Location tracking with Fragment-based UI | XML Layouts + View Binding |
| [09-3_GoogleMaps](./09-3_GoogleMaps) | Interactive map display with Google Maps Compose SDK | Jetpack Compose |
| [09-4_GoogleMaps-MDC](./09-4_GoogleMaps-MDC) | Google Maps with Fragment-based UI | XML Layouts + View Binding |

## Key Concepts Covered

- Fused Location Provider API (`FusedLocationProviderClient`)
- Location permissions: `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`
- Runtime permission requests and rationale handling
- Foreground services for continuous location updates
- Google Maps SDK integration and API key configuration
- Google Maps Compose library (`GoogleMap` composable)
- Map markers, camera positioning, and map controls
- Location-aware application design patterns

## Further Reading

- [Location overview](https://developer.android.com/develop/sensors-and-location/location)
- [Fused Location Provider](https://developers.google.com/location-context/fused-location-provider)
- [Google Maps SDK for Android](https://developers.google.com/maps/documentation/android-sdk/overview)
- [Maps Compose library](https://developers.google.com/maps/documentation/android-sdk/maps-compose)
- [Request location permissions](https://developer.android.com/develop/sensors-and-location/location/permissions)
