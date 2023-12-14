package com.puzzle_agency.androidknowledge.knowledge.deep_links


/**
 * Activity Intent filter - place in manifest
 *
 * <intent-filter android:autoVerify="true">
 *
 *  <action android:name="android.intent.action.VIEW" />
 *
 *  <category android:name="android.intent.category.DEFAULT" />
 *  <category android:name="android.intent.category.BROWSABLE" />
 *
 *  <data android:scheme="https" />
 *  <data android:host="domain.com" />
 *
 * </intent-filter>
 **/

/**
 * Useful resources
 *  - Generate and test asset links: https://developers.google.com/digital-asset-links/tools/generator
 *  - Debugging deep links: https://medium.com/androiddevelopers/deep-links-crash-course-part-3-troubleshooting-your-deep-links-61329fecb93
 *  - Complex paths https://proandroiddev.com/how-to-handle-deep-link-with-complex-path-like-this-811216a0c802
 */