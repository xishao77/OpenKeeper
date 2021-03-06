/*
 * Copyright (C) 2014-2015 OpenKeeper
 *
 * OpenKeeper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenKeeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenKeeper.  If not, see <http://www.gnu.org/licenses/>.
 */
package toniarts.openkeeper.game.player;

import com.jme3.app.state.AppStateManager;
import java.util.logging.Logger;
import toniarts.openkeeper.game.state.PlayerState;
import toniarts.openkeeper.game.trigger.TriggerControl;
import toniarts.openkeeper.game.trigger.TriggerGenericData;
import toniarts.openkeeper.tools.convert.map.TriggerGeneric;

/**
 *
 * @author ArchDemon
 */


public class PlayerTriggerControl extends TriggerControl {

    private PlayerState playerState = null;
    private static final Logger logger = Logger.getLogger(PlayerTriggerControl.class.getName());

    public PlayerTriggerControl() { // empty serialization constructor
        super();
    }

    public PlayerTriggerControl(final AppStateManager stateManager, int triggerId) {
        super(stateManager, triggerId);
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    @Override
    protected boolean isActive(TriggerGenericData trigger) {
        boolean result = super.isActive(trigger);
        if (checked) {
            return result;
        }

        result = false;
        int target = 0;
        int value = 0;

        TriggerGeneric.TargetType targetType = trigger.getType();
        switch (targetType) {
            case PLAYER_CREATURES:
                return false;
            case PLAYER_HAPPY_CREATURES:
                return false;
            case PLAYER_ANGRY_CREATURES:
                return false;
            case PLAYER_CREATURES_KILLED:
                return false;
            case PLAYER_KILLS_CREATURES:
                return false;
            case PLAYER_ROOM_SLABS:
                return false;
            case PLAYER_ROOMS:
                return false;
            case PLAYER_ROOM_SIZE:
                return false;
            case PLAYER_DOORS:
                return false;
            case PLAYER_TRAPS:
                return false;
            case PLAYER_KEEPER_SPELL:
                return false;
            case PLAYER_GOLD:
                PlayerGoldControl pgc = playerState.getGoldControl();
                target = pgc.getGold();
                boolean isValue = trigger.getUserData("flag", short.class) == 1;
                if (isValue) {
                    value = trigger.getUserData("value", int.class);
                } else {
                    // TODO get value from other player
                    short playerId = trigger.getUserData("targetId", short.class);
                    return false;
                }
                break;

            case PLAYER_GOLD_MINED:
                pgc = playerState.getGoldControl();
                target = pgc.getGoldMined();
                isValue = trigger.getUserData("flag", short.class) == 1;
                if (isValue) {
                    value = trigger.getUserData("value", int.class);
                } else {
                    // TODO get value from other player
                    short playerId = trigger.getUserData("targetId", short.class);
                    return false;
                }
                break;

            case PLAYER_MANA:
                PlayerManaControl pmc = playerState.getManaControl();
                target = pmc.getMana();
                break;

            case PLAYER_DESTROYS:
                return false;
            case PLAYER_CREATURES_AT_LEVEL:
                return false;
            case PLAYER_KILLED:
                return false;
            case PLAYER_DUNGEON_BREACHED:
                return false;
            case PLAYER_ENEMY_BREACHED:
                return false;
            case PLAYER_CREATURE_PICKED_UP:
                return false;
            case PLAYER_CREATURE_DROPPED:
                return false;
            case PLAYER_CREATURE_SLAPPED:
                return false;
            case PLAYER_CREATURE_SACKED:
                return false;
            case PLAYER_ROOM_FURNITURE:
                return false;
            case PLAYER_SLAPS:
                return false;
            case PLAYER_CREATURES_GROUPED:
                return false;
            case PLAYER_CREATURES_DYING:
                return false;
            case GUI_TRANSITION_ENDS:
                return playerState.isTransitionEnd();

            case GUI_BUTTON_PRESSED:
                return false;
            default:
                logger.warning("Target Type not supported");
                return false;
        }

        TriggerGeneric.ComparisonType comparisonType = trigger.getComparison();
        if (comparisonType != null && comparisonType != TriggerGeneric.ComparisonType.NONE) {
            result = compare(target, comparisonType, value);
        }

        return result;
    }
}
