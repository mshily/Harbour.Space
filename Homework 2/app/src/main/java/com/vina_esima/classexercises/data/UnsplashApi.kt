package com.vina_esima.classexercises.data

data class UnsplashApi(
    val blur_hash: String?,
    val color: String?,
    val created_at: String?,
    val current_user_collections: List<CurrentUserCollectionX>?,
    val description: String?,
    val downloads: Int?,
    val exif: Exif?,
    val height: Int?,
    val id: String?,
    val liked_by_user: Boolean?,
    val likes: Int?,
    val links: LinksXX?,
    val location: Location?,
    val public_domain: Boolean?,
    val tags: List<Tag>?,
    val updated_at: String?,
    val urls: UrlsX?,
    val user: UserX?,
    val width: Int?
)