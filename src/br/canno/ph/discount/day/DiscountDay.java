package br.canno.ph.discount.day;

public enum DiscountDay {

    MONDAY(false, "Base price"),
    TUESDAY(true, "Half price for motorcycles."),
    WEDNESDAY(true, "25% discount for vehicles with license plate contains the letter \"P\""),
    THURSDAY(true, "Starting price"),
    FRIDAY(true, "Half price for vans"),
    SATURDAY(true, "If the day is even half price, otherwise base price."),
    SUNDAY(false, "Base price");

    private boolean day;
    private String description;

    private DiscountDay(boolean day, String description) {
        this.day = day;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDay() {
        return day;
    }

}
