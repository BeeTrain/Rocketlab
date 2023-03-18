package io.rocketlab.screen.herosquad.presentation.view.game

import android.os.Parcelable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.herosquad.R
import io.rocketlab.screen.herosquad.presentation.view.game.dialog.HeroSquadMenuDialog
import io.rocketlab.screen.herosquad.presentation.viewmodel.HeroSquadViewModel
import io.rocketlab.ui.extension.update
import io.rocketlab.ui.swipe.cardstack.SwipeCardStack
import io.rocketlab.ui.swipe.cardstack.SwipeSide
import io.rocketlab.utils.extension.ifNull
import io.rocketlab.utils.extension.takeWhen
import kotlinx.parcelize.Parcelize
import java.util.UUID

private const val NOTHING_SELECTED = -1
private const val DECK_SIZE = 5
private const val CARD_RATIO = 12.0f / 16.0f

@Parcelize
data class Hero(
    val imageRes: Int,
    val uid: String = UUID.randomUUID().toString()
) : Parcelable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeroSquadGame(
    viewModel: HeroSquadViewModel
) {
    val menuDialogState by viewModel.gameMenuDialogState.collectAsState()
    val isDeckSelected = remember { mutableStateOf(false) }
    val addedCards = remember { mutableStateListOf<Hero>() }
    val selectedCardIndex = remember { mutableStateOf(NOTHING_SELECTED) }

    val list = listOf(
        R.drawable.spartan,
        R.drawable.mage,
        R.drawable.elf_druid,
        R.drawable.dwarf_tank,
        R.drawable.necromancer,
        R.drawable.werewolf_warrior,
    ).shuffled()

    Box(
        modifier = Modifier.fillMaxSize(),
        content = {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { viewModel.openGameMenuAction.accept() },
                imageVector = Icons.Filled.Menu,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.Center),
                visible = addedCards.size < DECK_SIZE
            ) {
                SwipeCardStack(
                    items = list,
                    onSwipe = { swipeAction ->
                        if (addedCards.size == DECK_SIZE) return@SwipeCardStack

                        when (swipeAction.side) {
                            SwipeSide.LEFT -> {
                                addedCards.add(Hero(swipeAction.item))
                                if (addedCards.size == DECK_SIZE) {
                                    isDeckSelected.value = true
                                }
                            }
                            SwipeSide.RIGHT -> {}
                            else -> Unit
                        }
                    },
                    isInfinite = true
                ) { item ->
                    Card(
                        modifier = Modifier
                            .height(200.dp)
                            .aspectRatio(CARD_RATIO)
                            .padding(horizontal = 8.dp),
                        border = BorderStroke(2.dp, Color.DarkGray),
                        colors = CardDefaults.cardColors(containerColor = Color.Gray),
                        content = {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(2.dp),
                                    painter = painterResource(id = item),
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            }
            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                visible = addedCards.isNotEmpty()
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                    contentPadding = PaddingValues(8.dp),
                ) {
                    itemsIndexed(
                        items = addedCards,
                        key = { _, card -> card }
                    ) { index, card ->
                        val isSelected by animateDpAsState(
                            16.dp.takeWhen(selectedCardIndex.value == index).ifNull(0.dp)
                        )
                        Card(
                            modifier = Modifier
                                .height(100.dp)
                                .aspectRatio(CARD_RATIO)
                                .offset(y = -isSelected)
                                .animateItemPlacement()
                                .clickable {
                                    when (selectedCardIndex.value) {
                                        index -> {
                                            addedCards.removeAt(index)
                                            selectedCardIndex.update { NOTHING_SELECTED }
                                            if (addedCards.isEmpty()) {
                                                isDeckSelected.value = false
                                            }
                                        }
                                        else -> selectedCardIndex.update { index }
                                    }
                                },
                            border = BorderStroke(2.dp, Color.DarkGray),
                            colors = CardDefaults.cardColors(containerColor = Color.Gray),
                            content = {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Image(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .padding(2.dp),
                                        painter = painterResource(id = card.imageRes),
                                        contentDescription = null
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    )
    HeroSquadMenuDialog(
        shouldShowDialog = menuDialogState,
        viewModel = viewModel
    )
}