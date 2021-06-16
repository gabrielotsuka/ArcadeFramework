package freezeMonsters;

public interface Commons extends spriteframework.Commons {
    int BOARD_WIDTH = 500;
    int BOARD_HEIGHT = 500;

    int SPRITE_WIDTH = 30;
    int SPRITE_HEIGHT = 50;

    int SHOT_WIDTH = 30;
    int SHOT_HEIGHT = 30;

    int SPRITE_RIGHT_BORDER = BOARD_WIDTH - SPRITE_WIDTH;
    int SPRITE_DOWN_BORDER = BOARD_HEIGHT - SPRITE_HEIGHT - 35;

    int NUMBER_OF_MONSTERS_TO_DESTROY = 9;

    int CHANCE = 5;
}
