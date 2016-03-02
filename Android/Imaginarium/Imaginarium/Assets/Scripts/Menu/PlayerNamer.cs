

public class PlayerNamer {

    private string[] names = { "Асбьёрн", "Гудрёд", "Кьярваль", "Мелькольв", "Рагнлейв", "Эйвинд" };


    public void SetNames(string[] tokens) {
        Prefs prefs = new Prefs();
        for (int i = 0; i < tokens.Length; i++)
            prefs.SetPlayerName(tokens[i], names[i]);
    }

}
