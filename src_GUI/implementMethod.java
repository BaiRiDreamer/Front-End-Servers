class implementMethod {
    public static void isValidInformation(String username, String uerID, String phone, String password1, String password2) throws Exception {
        //判断username合法性
        if (username.length() < 6 || username.length() > 20) {
            throw new Exception("用户名长度不合法");
        }
        if (!username.matches("[a-zA-Z0-9_]*")) {
            throw new Exception("用户名只能包含字母数字下划线");
        }
        if (uerID.length() != 18) {
            throw new Exception("用户ID长度不合法");
        }
        if (!uerID.matches("[0-9]*")) {
            throw new Exception("用户ID只能包含数字");
        }
        if (phone.length() != 11) {
            throw new Exception("手机号长度不合法");
        }
        if (!phone.matches("[0-9]*")) {
            throw new Exception("手机号只能包含数字");
        }
        if (password1.length() < 6 || password1.length() > 20) {
            throw new Exception("密码长度不合法");
        }
        if (!password1.matches("[a-zA-Z0-9_]*")) {
            throw new Exception("密码只能包含字母数字下划线");
        }
        if (!password1.equals(password2)) {
            throw new Exception("两次密码不一致");
        }
    }

    //判断用户输入城市是否合法
    public static void isCityInfoValid(String Country, String City) throws Exception {
        if (Country == null || City == null || Country.trim().equals("") || City.trim().equals("")) {
            throw new Exception("输入的城市信息为空，请重新输入。");
        }
        if (!Country.matches("[a-zA-Z]*") || !City.matches("[a-zA-Z]*")) {
            throw new Exception("输入的城市信息格式不正确，请重新输入。");
        }
        return;
    }

    //判断用户出入内容是否合法
    public static void isContentValid(String title, String content) throws Exception {
        if (title == null || content == null || title.trim().equals("") || content.trim().equals("")) {
            throw new Exception("输入的内容为空，请重新输入。");
        }
        if (!title.matches("[a-zA-Z]*")) {
            throw new Exception("输入的标题格式不符，请重新输入。");
        }
        return;
    }
}
