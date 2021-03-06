class Army {

    public static void createArmy() {
        String[] units = new String[] {"a", "b", "c", "d", "e"};
        for (int i = 0; i < 5; i++) {
            new Unit(units[i]);
        }
        
        String[] knights = new String[] {"f", "g", "h"};
        for (int j = 0; j < 3; j++) {
            new Knight(knights[j]);
        } 
        
        new General("i");
        new Doctor("j");
        
        
        
    }


    // Don't change the code below
    static class Unit {
        static String nameUnit;
        static int countUnit;

        public Unit(String name) {
            countUnit++;
            nameUnit = name;

        }
    }

    static class Knight {
        static String nameKnight;
        static int countKnight;

        public Knight(String name) {
            countKnight++;
            nameKnight = name;

        }
    }

    static class General {
        static String nameGeneral;
        static int countGeneral;

        public General(String name) {
            countGeneral++;
            nameGeneral = name;

        }
    }

    static class Doctor {
        static String nameDoctor;
        static int countDoctor;

        public Doctor(String name) {
            countDoctor++;
            nameDoctor = name;

        }
    }

    public static void main(String[] args) {
        createArmy();
        System.out.println(Unit.countUnit);
        System.out.println(Knight.countKnight);
        System.out.println(General.countGeneral);
        System.out.println(Doctor.countDoctor);
    }

}
