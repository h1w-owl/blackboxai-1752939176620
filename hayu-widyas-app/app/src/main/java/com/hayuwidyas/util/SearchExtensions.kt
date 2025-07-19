package com.hayuwidyas.util

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

/**
 * Search utility class with debounce functionality
 */
class SearchWithDebounce(
    private val debounceMs: Long = 300L,
    private val minimumLength: Int = 3
) {
    private val _searchFlow = MutableStateFlow("")
    
    /**
     * Observable search query flow with debounce and filtering
     */
    @OptIn(FlowPreview::class)
    val searchFlow: Flow<String> = _searchFlow
        .debounce(debounceMs)
        .filter { it.length >= minimumLength || it.isEmpty() }
        .distinctUntilChanged()
    
    /**
     * Update the search query
     */
    fun updateQuery(query: String) {
        _searchFlow.value = query.trim()
    }
    
    /**
     * Clear the search query
     */
    fun clearQuery() {
        _searchFlow.value = ""
    }
    
    /**
     * Get current query value
     */
    fun getCurrentQuery(): String = _searchFlow.value
}

/**
 * Extension function for Flow to add debounce search functionality
 */
@OptIn(FlowPreview::class)
fun Flow<String>.debounceSearch(
    debounceMs: Long = 300L,
    minimumLength: Int = 3
): Flow<String> = this
    .debounce(debounceMs)
    .filter { it.length >= minimumLength || it.isEmpty() }
    .distinctUntilChanged()

/**
 * Extension function to create a search flow that emits normalized queries
 */
@OptIn(FlowPreview::class)
fun Flow<String>.normalizedSearch(
    debounceMs: Long = 300L,
    minimumLength: Int = 3
): Flow<String> = this
    .map { it.trim().lowercase() }
    .debounce(debounceMs)
    .filter { it.length >= minimumLength || it.isEmpty() }
    .distinctUntilChanged()

/**
 * Search query validation
 */
object SearchValidator {
    
    /**
     * Validate search query
     */
    fun isValidQuery(query: String, minimumLength: Int = 3): Boolean {
        val trimmed = query.trim()
        return trimmed.isEmpty() || trimmed.length >= minimumLength
    }
    
    /**
     * Sanitize search query
     */
    fun sanitizeQuery(query: String): String {
        return query.trim()
            .replace(Regex("[^a-zA-Z0-9\\s-_]"), "")
            .replace(Regex("\\s+"), " ")
    }
    
    /**
     * Extract search terms from query
     */
    fun extractSearchTerms(query: String): List<String> {
        return sanitizeQuery(query)
            .split(" ")
            .filter { it.isNotBlank() }
    }
}

/**
 * Search result highlighting utility
 */
object SearchHighlighter {
    
    /**
     * Highlight search terms in text
     */
    fun highlightTerms(
        text: String,
        searchQuery: String,
        highlightStart: String = "<mark>",
        highlightEnd: String = "</mark>"
    ): String {
        if (searchQuery.isBlank()) return text
        
        val terms = SearchValidator.extractSearchTerms(searchQuery)
        var highlightedText = text
        
        terms.forEach { term ->
            if (term.length >= 2) {
                highlightedText = highlightedText.replace(
                    term,
                    "$highlightStart$term$highlightEnd",
                    ignoreCase = true
                )
            }
        }
        
        return highlightedText
    }
    
    /**
     * Calculate relevance score for search results
     */
    fun calculateRelevanceScore(
        text: String,
        searchQuery: String
    ): Float {
        if (searchQuery.isBlank()) return 0f
        
        val normalizedText = text.lowercase()
        val terms = SearchValidator.extractSearchTerms(searchQuery.lowercase())
        
        var score = 0f
        terms.forEach { term ->
            if (normalizedText.contains(term)) {
                score += when {
                    normalizedText.startsWith(term) -> 3f
                    normalizedText.contains(" $term") -> 2f
                    else -> 1f
                }
            }
        }
        
        return score / terms.size
    }
}

/**
 * Search history manager
 */
class SearchHistoryManager(
    private val maxHistorySize: Int = 10
) {
    private val _searchHistory = mutableListOf<String>()
    
    /**
     * Add query to search history
     */
    fun addToHistory(query: String) {
        val trimmed = query.trim()
        if (trimmed.isNotBlank() && trimmed.length >= 3) {
            _searchHistory.remove(trimmed) // Remove if already exists
            _searchHistory.add(0, trimmed) // Add to beginning
            
            // Maintain max size
            if (_searchHistory.size > maxHistorySize) {
                _searchHistory.removeAt(_searchHistory.size - 1)
            }
        }
    }
    
    /**
     * Get search history
     */
    fun getHistory(): List<String> = _searchHistory.toList()
    
    /**
     * Clear search history
     */
    fun clearHistory() {
        _searchHistory.clear()
    }
    
    /**
     * Remove specific query from history
     */
    fun removeFromHistory(query: String) {
        _searchHistory.remove(query)
    }
    
    /**
     * Get filtered suggestions based on current input
     */
    fun getSuggestions(currentInput: String): List<String> {
        if (currentInput.isBlank()) return getHistory()
        
        return _searchHistory.filter { 
            it.contains(currentInput.trim(), ignoreCase = true) 
        }
    }
}