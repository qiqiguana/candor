package original;


import java.util.List;


class StrongestExtension {
    /**
     * You will be given the name of a class (a string) and a list of extensions.
     * The extensions are to be used to load additional classes to the class. The
     * strength of the extension is as follows: Let CAP be the number of the uppercase
     * letters in the extension's name, and let SM be the number of lowercase letters
     * in the extension's name, the strength is given by the fraction CAP - SM.
     * You should find the strongest extension and return a string in this
     * format: ClassName.StrongestExtensionName.
     * If there are two or more extensions with the same strength, you should
     * choose the one that comes first in the list.
     * For example, if you are given "Slices" as the class and a list of the
     * extensions: ['SErviNGSliCes', 'Cheese', 'StuFfed'] then you should
     * return 'Slices.SErviNGSliCes' since 'SErviNGSliCes' is the strongest extension
     * (its strength is -1).
     * Example:
     * for strongestExtension('my_class', ['AA', 'Be', 'CC']) == 'my_class.AA'
     *
     * > strongestExtension("Watashi", ["tEN", "niNE", "eIGHt8OKe"])
     * "Watashi.eIGHt8OKe"
     * > strongestExtension("Boku123", ["nani", "NazeDa", "YEs.WeCaNe", "32145tggg"])
     * "Boku123.YEs.WeCaNe"
     * > strongestExtension("__YESIMHERE", ["t", "eMptY", "nothing", "zeR00", "NuLl__", "123NoooneB321"])
     * "__YESIMHERE.NuLl__"
     * > strongestExtension("K", ["Ta", "TAR", "t234An", "cosSo"])
     * "K.TAR"
     * > strongestExtension("__HAHA", ["Tab", "123", "781345", "-_-"])
     * "__HAHA.123"
     * > strongestExtension("YameRore", ["HhAas", "okIWILL123", "WorkOut", "Fails", "-_-"])
     * "YameRore.okIWILL123"
     * > strongestExtension("finNNalLLly", ["Die", "NowW", "Wow", "WoW"])
     * "finNNalLLly.WoW"
     * > strongestExtension("_", ["Bb", "91245"])
     * "_.Bb"
     * > strongestExtension("Sp", ["671235", "Bb"])
     * "Sp.671235"
     */
    public static String strongestExtension(String className, List<String> extensions) {
        int max = Integer.MIN_VALUE;
        String result = "";
        for (int i = 0; i < extensions.size(); i++) {
            int cap = 0;
            int sm = 0;
            for (int j = 0; j < extensions.get(i).length(); j++) {
                if (Character.isUpperCase(extensions.get(i).charAt(j))) {
                    cap++;
                } else if (Character.isLowerCase(extensions.get(i).charAt(j))) {
                    sm++;
                }
            }
            if (cap - sm > max) {
                max = cap - sm;
                result = className + "." + extensions.get(i);
            }
        }
        return result;
    }


}