package io.rocketlab.screen.herosquad.presentation.model

sealed interface HeroSquadScreenState {

    object Menu : HeroSquadScreenState

    object Game : HeroSquadScreenState
}